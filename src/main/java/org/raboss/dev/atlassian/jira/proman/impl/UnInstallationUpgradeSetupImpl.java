package org.raboss.dev.atlassian.jira.proman.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import org.raboss.dev.atlassian.jira.proman.api.UnInstallationUpgradeSetup;
import org.raboss.dev.atlassian.jira.proman.entity.EvalCriterion1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

/**
 * Created by bossekr on 09.03.16.
 * p.71
 * https://answers.atlassian.com/questions/32977748/bitbucket-eventlistener-is-not-called
 */
@ExportAsService({UnInstallationUpgradeSetup.class})
@Named("eventListener")
public class UnInstallationUpgradeSetupImpl implements UnInstallationUpgradeSetup, InitializingBean, DisposableBean
{
    @ComponentImport
    final protected ActiveObjects ao;

    static final private Logger log;

    static {
        log = LoggerFactory.getLogger(UnInstallationUpgradeSetupImpl.class);
    }

    @Inject
    public UnInstallationUpgradeSetupImpl(@NotNull final ActiveObjects ao)
    {
        this.ao = ao;
    }

    /**
     * Handle plugin disabling or un-installation here.
     * @throws Exception
     */
    public void destroy() throws Exception
    {
        log.debug("destroy()");
    }

    /**
     * Handle plugin enabling or installation here.
     * @throws Exception
     */
    public void afterPropertiesSet() throws Exception {
        log.debug("afterPropertiesSet()");
        EvalCriterion1[] ecs = ao.get(EvalCriterion1.class);
        if (ecs.length == 0)
        {
            // Strategic Fit
            EvalCriterion1 ec = ao.create(EvalCriterion1.class);
            ec.setName("Alignment with Company Goals");
            ec.setDescription("How aligned is this project to corporate goals & objectives?");
            ec.setWeighting(15);
            ec.setTypeOfIndex(EvalCriterion1.TypeOfIndex.PERCENTAGE);
            ec.setIsBigNumberIsBetter(true);
            ec.save();
            ec = ao.create(EvalCriterion1.class);
            ec.setName("Market Positioning");
            ec.setDescription("Does this initiative position us better in the market?");
            ec.setWeighting(20);
            ec.setTypeOfIndex(EvalCriterion1.TypeOfIndex.PERCENTAGE);
            ec.setIsBigNumberIsBetter(true);
            ec.save();
            ec = ao.create(EvalCriterion1.class);
            ec.setName("Core Capabilities");
            ec.setDescription("Does this initiative leverage our core capabilities (technology, operations, sales)?");
            ec.setWeighting(5);
            ec.setTypeOfIndex(EvalCriterion1.TypeOfIndex.PERCENTAGE);
            ec.setIsBigNumberIsBetter(true);
            ec.save();
            // Economical Impact
            ec = ao.create(EvalCriterion1.class);
            ec.setName("Revenue Potential");
            ec.setDescription("What is the anticipated impact on revenue for this initiative.");
            ec.setWeighting(15);
            ec.setTypeOfIndex(EvalCriterion1.TypeOfIndex.PERCENTAGE);
            ec.setIsBigNumberIsBetter(true);
            ec.save();
            ec = ao.create(EvalCriterion1.class);
            ec.setName("Cost/Benefit");
            ec.setDescription("Does this initiative have a solid cost/benefit?");
            ec.setWeighting(20);
            ec.setTypeOfIndex(EvalCriterion1.TypeOfIndex.PERCENTAGE);
            ec.setIsBigNumberIsBetter(true);
            ec.save();
            ec = ao.create(EvalCriterion1.class);
            ec.setName("Low Cost");
            ec.setDescription("Is this project relatively low-cost?");
            ec.setWeighting(5);
            ec.setTypeOfIndex(EvalCriterion1.TypeOfIndex.PERCENTAGE);
            ec.setIsBigNumberIsBetter(true);
            ec.save();
            // Feasibility
            ec = ao.create(EvalCriterion1.class);
            ec.setName("Technical Risk");
            ec.setDescription("What is the probability of overcoming the technical challenges of the project?");
            ec.setWeighting(10);
            ec.setTypeOfIndex(EvalCriterion1.TypeOfIndex.PERCENTAGE);
            ec.setIsBigNumberIsBetter(true);
            ec.save();
            ec = ao.create(EvalCriterion1.class);
            ec.setName("Resources - Financial");
            ec.setDescription("Do we have the financial resources to execute this initiative?");
            ec.setWeighting(5);
            ec.setTypeOfIndex(EvalCriterion1.TypeOfIndex.PERCENTAGE);
            ec.setIsBigNumberIsBetter(true);
            ec.save();
            ec = ao.create(EvalCriterion1.class);
            ec.setName("Resources - People");
            ec.setDescription("Do we have the skills & bandwidth to execute this initiative?");
            ec.setWeighting(5);
            ec.setTypeOfIndex(EvalCriterion1.TypeOfIndex.PERCENTAGE);
            ec.setIsBigNumberIsBetter(true);
            ec.save();
            ecs = ao.get(EvalCriterion1.class);
        }
        for(EvalCriterion1 ec : ecs)
        {
            log.debug("ec.Name={}", ec.getName());
        }
    }
}
