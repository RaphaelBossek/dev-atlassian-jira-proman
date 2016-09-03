package org.raboss.dev.atlassian.jira.proman.ui;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.config.properties.APKeys;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import org.raboss.dev.atlassian.jira.proman.api.rest.EvaluationCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manage (create,copy,modify,delete) the evaluation criteria in the
 * UI. Evaluation criteria can be e.g.
 *  - Alignment with Company Goals: How aligned is this project to corporate goals & objectives?
 *  - Revenue Potential: What is the anticipated impact on revenue for this initiative.
 *  - Technical Risk: What is the probability of overcoming the technical challenges of the project?
 */
@Scanned
public class ProManEvaluationCriteria extends JiraWebActionSupport {
    static final private Logger log;

    static {
        log = LoggerFactory.getLogger(ProManEvaluationCriteria.class);
    }

    @Override
    public String doExecute() throws Exception {
        log.debug("Current action name is " + getActionName());
        return SUCCESS;
    }

    //@RequiresXsrfCheck
    public String doCreateEvaluationCriteria() throws Exception {
        log.debug("doCreateEvaluationCriteria");
        log.debug("parameter[evaluation_criterion_name_text_input]={}, parameter[evaluation_criterion_type_select_input]={}", getHttpRequest().getParameter("evaluation_criterion_name_text_input").toString(), getHttpRequest().getParameter("evaluation_criterion_type_select_input").toString());
        return returnComplete();
    }

    public String doEditEvaluationCriterion() throws Exception {
        log.debug("doEditEvaluationCriterion");
        return returnComplete();
    }

    public String doCopyEvaluationCriterion() throws Exception {
        log.debug("doCopyEvaluationCriterion");
        return returnComplete();
    }

    public String doDeleteEvaluationCriterion() throws Exception {
        log.debug("doDeleteEvaluationCriterion");
        return returnComplete();
    }

    public String getContextPath() throws Exception {
        return ComponentAccessor.getApplicationProperties().getString(APKeys.JIRA_BASEURL) + EvaluationCriteria.RESTCONTEXTPATH;
    }
}
