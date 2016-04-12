package org.raboss.dev.atlassian.jira.proman.ui;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import net.java.ao.Query;
import org.raboss.dev.atlassian.jira.proman.entity.EvalCriterion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Manage (create,copy,modify,delete) the evaluation criteria in the
 * UI. Evaluation criteria can be e.g.
 *  - Alignment with Company Goals: How aligned is this project to corporate goals & objectives?
 *  - Revenue Potential: What is the anticipated impact on revenue for this initiative.
 *  - Technical Risk: What is the probability of overcoming the technical challenges of the project?
 */
@Scanned
public class ProManEvaluationCriteria extends JiraWebActionSupport {
    @ComponentImport
    final private ActiveObjects activeObjects;

    static final private Logger log;

    static {
        log = LoggerFactory.getLogger(ProManEvaluationCriteria.class);
    }

    @Inject
    ProManEvaluationCriteria(final ActiveObjects activeObjects) {
        this.activeObjects = activeObjects;
    }

    @Override
    public String doExecute() throws Exception {
        log.debug("Current action name is " + getActionName());
        return SUCCESS;
    }

    /**
     * TODO: Definition of maximum number of evaluation criteria depending on the license
     * TODO: Definition of maximum number of evaluation criteria per page
     * TODO: Paging between lists of evaluation criteria
     * @return
     */
    public List<HashMap<String,String>> getAllEvaluationCriteria() {
        List<HashMap<String,String>> l = new ArrayList<HashMap<String,String>>();
        log.debug("getAllEvaluationCriteria()");
        EvalCriterion[] ecs = activeObjects.find(EvalCriterion.class, Query.select().limit(25));
        if(ecs.length != 0) {
            for(EvalCriterion ec : ecs) {
                log.debug("name:{}, type:{}, comment:{}", ec.getName(), ec.getTypeOfIndex().toString(), ec.getComment());
                HashMap<String,String> kw = new HashMap<String, String>();
                kw.put("evaluation-criterion-id", String.valueOf(ec.getID()));
                kw.put("evaluation-criterion-name", ec.getName());
                kw.put("evaluation-criterion-type", ec.getTypeOfIndex().toString());
                kw.put("evaluation-criterion-usage", "");
                kw.put("evaluation-criterion-owner", "");
                kw.put("evaluation-criterion-comment", ec.getComment());
                kw.put("evaluation-criterion-operation-edit", "on");
                kw.put("evaluation-criterion-operation-copy", "on");
                kw.put("evaluation-criterion-operation-delete", "on");
                l.add(kw);
            }
        }
        return l;
    }
}
