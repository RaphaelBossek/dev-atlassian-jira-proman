package org.raboss.dev.atlassian.jira.proman.ui;

import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.templaterenderer.TemplateRenderer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;

import static webwork.action.ActionContext.getLocale;
import static webwork.action.ActionContext.setContext;

/**
 * Created by bossekr on 06.03.16.
 */
public class ProManAdministration extends JiraWebActionSupport {
    @Override
    public String doExecute() throws Exception {
        // TODO: If there are no polls, and command is not specified we jump to one of the best suitable menus
        log.debug("Current action name is " + getActionName());
        return getRedirect("/secure/ProManAdministration!EvaluationCriteria.jspa");
    }

    /**
     * This function is called for the action as &lt;command name="evalutionCriteria"&gt;.
     * @return String The next template name to be shown as defined in the build-in symphony-webworks configuration.
     * @throws Exception
     */
    public String doEvaluationCriteria() throws Exception {
        // TODO: If there are no polls, and command is not specified we jump to one of the best suitable menus
        log.debug("Current action name is " + getActionName());
        return getRedirect("/secure/ProManEvaluationCriteria.jspa");
    }

    public String doPools() throws Exception {
        // TODO: If there are no polls, and command is not specified we jump to one of the best suitable menus
        log.debug("Current action name is " + getActionName());
        return getRedirect("/secure/ProManPolls.jspa");
    }

    public String doEvaluationCategories() throws Exception {
        // TODO: If there are no polls, and command is not specified we jump to one of the best suitable menus
        log.debug("Current action name is " + getActionName());
        return getRedirect("/secure/ProManEvaluationCategories.jspa");
    }
}
