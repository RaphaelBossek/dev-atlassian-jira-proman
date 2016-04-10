package org.raboss.dev.atlassian.jira.proman.ui;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.plugin.spring.scanner.annotation.component.ClasspathComponent;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import org.raboss.dev.atlassian.jira.proman.entity.EvalCriterion1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.l;

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

    public List<HashMap<String,String>> getAllEvaluationCriteria() {
        log.debug("getAllEvaluationCriteria()");
        EvalCriterion1[] ecs = activeObjects.get(EvalCriterion1.class);
        if(ecs.length != 0) {
            for(EvalCriterion1 ec : ecs) {
                log.debug("name:{}, type:{}, comment:{}", ec.getName(), ec.getTypeOfIndex().toString(), ec.getComment());
            }
        }
        List<HashMap<String,String>> l = new ArrayList<HashMap<String,String>>();
        l.add(new HashMap<String,String>());
        return l;
    }
}
