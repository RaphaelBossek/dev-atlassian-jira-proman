package org.raboss.dev.atlassian.jira.proman.ui;

import com.atlassian.jira.web.action.JiraWebActionSupport;

/**
 * Created by bossekr on 09.04.16.
 */
public class ProManPolls extends JiraWebActionSupport {
    @Override
    public String doExecute() throws Exception {
        log.debug("Current action name is " + getActionName());
        return SUCCESS;
    }
}
