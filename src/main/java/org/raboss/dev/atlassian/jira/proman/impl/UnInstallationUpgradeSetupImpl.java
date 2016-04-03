package org.raboss.dev.atlassian.jira.proman.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.plugin.event.events.PluginEnabledEvent;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.lifecycle.LifecycleAware;
import com.atlassian.sal.api.lifecycle.LifecycleManager;
//import com.atlassian.sal.jira.lifecycle.JiraLifecycleManager;
import org.raboss.dev.atlassian.jira.proman.entity.EvalCriterion1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.concurrent.GuardedBy;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.EnumSet;
import java.util.Set;

// Workarround for multiple annotations of @Named
@interface NamedMul {
    Named[] value();
}

/**
 * Created by bossekr on 09.03.16.
 * p.71
 * https://answers.atlassian.com/questions/37549499/answers/38059259
 * https://answers.atlassian.com/questions/32977748/bitbucket-eventlistener-is-not-called
 * https://bitbucket.org/cfuller/atlassian-scheduler-jira-example/src/371cbc419c5a4fa3197d4dc28ddeb21105718a43/src/main/java/com/atlassian/jira/plugins/example/scheduler/impl/AwesomeLauncher.java?at=master&fileviewer=file-view-default
 * https://docs.atlassian.com/sal-api/2.11.6/sal-api/apidocs/index.html?com/atlassian/sal/api/lifecycle/LifecycleAware.html
 * https://developer.atlassian.com/jiradev/jira-platform/building-jira-add-ons/jira-plugins2-overview/jira-plugin-lifecycle
 */
@Scanned
//@NamedMul({@Named("eventListener"), @Named("awesomeLauncher")})
@Named("eventListener")
public class UnInstallationUpgradeSetupImpl implements LifecycleAware, InitializingBean, DisposableBean
{
    static private final String PLUGIN_KEY="org.raboss.dev.atlassian.jira.proman";
    @ComponentImport
    protected final ActiveObjects activeObjects;
    @ComponentImport
    protected final EventPublisher eventPublisher;
    protected final LifecycleManager lifecycleManager;

    static final private Logger log;

    /**
     * Used to keep track of everything that needs to happen before we are sure that it is safe
     * to talk to all of the components we need to use, particularly the {@code SchedulerService}
     * and Active Objects.  We will not try to initialize until all of them have happened.
     */
    enum LifecycleEvent
    {
        AFTER_PROPERTIES_SET,
        PLUGIN_ENABLED,
        LIFECYCLE_AWARE_ON_START
    }
    @GuardedBy("this")
    private final Set<LifecycleEvent> lifecycleEvents = EnumSet.noneOf(LifecycleEvent.class);

    static {
        log = LoggerFactory.getLogger(UnInstallationUpgradeSetupImpl.class);
    }

    @Inject
    public UnInstallationUpgradeSetupImpl(final ActiveObjects activeObjects, final EventPublisher eventPublisher, final LifecycleManager lifecycleManager)
    {
        this.activeObjects = activeObjects;
        this.eventPublisher = eventPublisher;
        this.lifecycleManager = lifecycleManager;
    }

    /**
     * This is received from Spring after the bean's properties are set.  We need to accept this to know when
     * it is safe to register an event listener.
     */
    @Override
    public void afterPropertiesSet()
    {
        registerListener();
        onLifecycleEvent(LifecycleEvent.AFTER_PROPERTIES_SET);
    }

    /**
     * This is received from SAL after the system is really up and running from its perspective.  This includes
     * things like the database being set up and other tricky things like that.  This needs to happen before we
     * try to schedule anything, or the scheduler's tables may not be in a good state on a clean install.
     */
    @Override
    public void onStart()
    {
        onLifecycleEvent(LifecycleEvent.LIFECYCLE_AWARE_ON_START);
    }

    /**
     * This is received from the plugin system after the plugin is fully initialized.  It is not safe to use
     * Active Objects before this event is received.
     */
    @EventListener
    public void onPluginEnabled(PluginEnabledEvent event)
    {
        if (PLUGIN_KEY.equals(event.getPlugin().getKey()))
        {
            onLifecycleEvent(LifecycleEvent.PLUGIN_ENABLED);
        }
    }

    /**
     * This is received from Spring when we are getting destroyed.  We should make sure we do not leave any
     * event listeners or job runners behind; otherwise, we could leak the current plugin context, leading to
     * exceptions from destroyed OSGi proxies, memory leaks, and strange behaviour in general.
     */
    @Override
    public void destroy() throws Exception
    {
        unregisterListener();
    }

    /**
     * The latch which ensures all of the plugin/application lifecycle progress is completed before we call
     * {@code launch()}.
     */
    private void onLifecycleEvent(LifecycleEvent event)
    {
        log.info("onLifecycleEvent: " + event);
        if (isLifecycleReady(event))
        {
            log.info("Got the last lifecycle event... Time to get started!");
            unregisterListener();

            try
            {
                launch();
            }
            catch (Exception ex)
            {
                log.error("Unexpected error during launch", ex);
            }
        }
    }

    /**
     * The event latch.
     * <p>
     * When something related to the plugin initialization happens, we call this with
     * the corresponding type of the event.  We will return {@code true} at most once, when the very last type
     * of event is triggered.  This method has to be {@code synchronized} because {@code EnumSet} is not
     * thread-safe and because we have multiple accesses to {@code lifecycleEvents} that need to happen
     * atomically for correct behaviour.
     * </p>
     *
     * @param event the lifecycle event that occurred
     * @return {@code true} if this completes the set of initialization-related events; {@code false} otherwise
     */
    synchronized private boolean isLifecycleReady(LifecycleEvent event)
    {
        log.debug("lifecycleEvents.size() == {} ({})", lifecycleEvents.size()+1, LifecycleEvent.values().length);
        return lifecycleEvents.add(event) && lifecycleEvents.size() == LifecycleEvent.values().length;
    }


    /**
     * Do all the things we can't do before the system is fully up.
     */
    private void launch() throws Exception
    {
        log.info("LAUNCH!");
        initActiveObjects();
        log.info("launched successfully");
    }

    private void registerListener()
    {
        log.info("registerListeners");
        eventPublisher.register(this);
    }

    private void unregisterListener()
    {
        log.info("unregisterListeners");
        eventPublisher.unregister(this);
    }

    /**
     * Prod AO to make sure it is really and truly ready to go.  If AO needs to do things like upgrade the
     * schema or if it is going to completely blow up on us, then hopefully that will happen here.  If we
     * don't do this, then AO will do all of these things when we first touch it at some arbitrary other
     * point in the code, meaning that the place where the upgrades, failures, etc. happen might not be
     * deterministic.  Explicitly prodding AO here makes the system more deterministic and therefore easier
     * to troubleshoot.
     * <p/>
     * Note that this is not necessary for AO 0.26 onwards (JIRA 6.4 and later), as AO is initialised as
     * soon as it can be &mdash; that is, once the {@code <ao>} configuration module and a data source
     * are both present.
     */
    private void initActiveObjects()
    {
        log.info("initActiveObjects");
        activeObjects.flushAll();
        EvalCriterion1[] ecs = activeObjects.get(EvalCriterion1.class);
        if (ecs.length == 0)
        {
            // Strategic Fit
            EvalCriterion1 ec = activeObjects.create(EvalCriterion1.class);
            ec.setName("Alignment with Company Goals");
            ec.setDescription("How aligned is this project to corporate goals & objectives?");
            ec.setWeighting(15);
            ec.setTypeOfIndex(EvalCriterion1.TypeOfIndex.PERCENTAGE);
            ec.setIsBigNumberIsBetter(true);
            ec.save();
            ec = activeObjects.create(EvalCriterion1.class);
            ec.setName("Market Positioning");
            ec.setDescription("Does this initiative position us better in the market?");
            ec.setWeighting(20);
            ec.setTypeOfIndex(EvalCriterion1.TypeOfIndex.PERCENTAGE);
            ec.setIsBigNumberIsBetter(true);
            ec.save();
            ec = activeObjects.create(EvalCriterion1.class);
            ec.setName("Core Capabilities");
            ec.setDescription("Does this initiative leverage our core capabilities (technology, operations, sales)?");
            ec.setWeighting(5);
            ec.setTypeOfIndex(EvalCriterion1.TypeOfIndex.PERCENTAGE);
            ec.setIsBigNumberIsBetter(true);
            ec.save();
            // Economical Impact
            ec = activeObjects.create(EvalCriterion1.class);
            ec.setName("Revenue Potential");
            ec.setDescription("What is the anticipated impact on revenue for this initiative.");
            ec.setWeighting(15);
            ec.setTypeOfIndex(EvalCriterion1.TypeOfIndex.PERCENTAGE);
            ec.setIsBigNumberIsBetter(true);
            ec.save();
            ec = activeObjects.create(EvalCriterion1.class);
            ec.setName("Cost/Benefit");
            ec.setDescription("Does this initiative have a solid cost/benefit?");
            ec.setWeighting(20);
            ec.setTypeOfIndex(EvalCriterion1.TypeOfIndex.PERCENTAGE);
            ec.setIsBigNumberIsBetter(true);
            ec.save();
            ec = activeObjects.create(EvalCriterion1.class);
            ec.setName("Low Cost");
            ec.setDescription("Is this project relatively low-cost?");
            ec.setWeighting(5);
            ec.setTypeOfIndex(EvalCriterion1.TypeOfIndex.PERCENTAGE);
            ec.setIsBigNumberIsBetter(true);
            ec.save();
            // Feasibility
            ec = activeObjects.create(EvalCriterion1.class);
            ec.setName("Technical Risk");
            ec.setDescription("What is the probability of overcoming the technical challenges of the project?");
            ec.setWeighting(10);
            ec.setTypeOfIndex(EvalCriterion1.TypeOfIndex.PERCENTAGE);
            ec.setIsBigNumberIsBetter(true);
            ec.save();
            ec = activeObjects.create(EvalCriterion1.class);
            ec.setName("Resources - Financial");
            ec.setDescription("Do we have the financial resources to execute this initiative?");
            ec.setWeighting(5);
            ec.setTypeOfIndex(EvalCriterion1.TypeOfIndex.PERCENTAGE);
            ec.setIsBigNumberIsBetter(true);
            ec.save();
            ec = activeObjects.create(EvalCriterion1.class);
            ec.setName("Resources - People");
            ec.setDescription("Do we have the skills & bandwidth to execute this initiative?");
            ec.setWeighting(5);
            ec.setTypeOfIndex(EvalCriterion1.TypeOfIndex.PERCENTAGE);
            ec.setIsBigNumberIsBetter(true);
            ec.save();
            ecs = activeObjects.get(EvalCriterion1.class);
        }
        for(EvalCriterion1 ec : ecs)
        {
            log.debug("ec.Name={}", ec.getName());
        }
    }
}
