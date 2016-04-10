package org.raboss.dev.atlassian.jira.proman.ui;

import com.atlassian.jira.util.I18nHelper;
import com.atlassian.jira.util.velocity.VelocityRequestContext;
import com.atlassian.jira.util.velocity.VelocityRequestContextFactory;
import com.atlassian.jira.web.bean.I18nBean;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.plugin.web.api.WebItem;
import com.atlassian.plugin.web.api.model.WebFragmentBuilder;
import com.atlassian.plugin.web.api.provider.WebItemProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by bossekr on 05.03.16.
 * @link https://answers.atlassian.com/questions/11970940/how-to-use-webitemprovider-since-simplelinkfactory-is-deprecated
 */
@Scanned
public class TopNavigationBarMenu implements WebItemProvider {
    @ComponentImport
    private final VelocityRequestContextFactory velocityRequestContextFactory;
    @ComponentImport
    private final I18nBean.BeanFactory beanFactory;

    private static final Logger log;

    static {
        log = LoggerFactory.getLogger(TopNavigationBarMenu.class);
    }

    public TopNavigationBarMenu(VelocityRequestContextFactory velocityRequestContextFactory, I18nBean.BeanFactory beanFactory)
    {
        this.velocityRequestContextFactory = velocityRequestContextFactory;
        this.beanFactory = beanFactory;
    }
    public Iterable<WebItem> getItems(Map<String, Object> map) {
        log.info("getItems()");
        final List<WebItem> links = new ArrayList<WebItem>();
        final VelocityRequestContext requestContext = velocityRequestContextFactory.getJiraVelocityRequestContext();
        //final User user = (User)map.get("user");
        final I18nHelper i18n = (I18nHelper) map.get("i18n");
        // Need ot ensure they contain the baseurl in case they are loaded via ajax/rest
        final String baseUrl = requestContext.getBaseUrl();
        int weight = 102;
        links.add(new WebFragmentBuilder(weight++)
            .id("proman-more-scoring-polls")
            .label(i18n.getText("org.raboss.dev.atlassian.jira.proman.menu.more_scoring_polls"))
            .title(i18n.getText("org.raboss.dev.atlassian.jira.proman.menu.more_scoring_polls.tooltip"))
            .webItem("proman-menu/proman-scoring-polls")
            .url(baseUrl + "/plugins/servlet/proman/admin")
            .build());
        links.add(new WebFragmentBuilder(weight++)
            .id("proman-show-users-project-permissions")
            .label("ShowUsersProjectPermssions")
            .webItem("proman-menu/ShowUsersProjectPermssions")
            .url(baseUrl + "/secure/ShowUsersProjectPermissionAction.jspa?permission=create")
            .build());
        return links;
    }
}
