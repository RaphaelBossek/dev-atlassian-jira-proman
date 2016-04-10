package org.raboss.dev.atlassian.jira.proman.ui;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.web.action.JiraWebActionSupport;
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
 * Created by bossekr on 09.04.16.
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
