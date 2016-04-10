package org.raboss.dev.atlassian.jira.proman.ui;

import com.atlassian.jira.project.Project;
import com.atlassian.jira.security.PermissionManager;
import com.atlassian.jira.security.Permissions;
import com.atlassian.jira.security.plugin.ProjectPermissionKey;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.user.ApplicationUsers;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.user.UserManager;
import com.atlassian.sal.api.user.UserProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Collection;

/**
 * Basic webwork action, that will retrieve all the projects that the current user has a particular permission in.
 * <p/>
 * For example the current user may wish to see all the project that he/she has the 'Create Issue' permission in.
 */
@Scanned
public class ShowUsersProjectPermissionAction extends JiraWebActionSupport
{
    @ComponentImport
    private final PermissionManager permissionManager;
    @ComponentImport
    private final UserManager userManager;
    private String permission;
    private Collection<Project> projects;

    private static final Logger log;

    static {
        log = LoggerFactory.getLogger(ShowUsersProjectPermissionAction.class);
    }

    @Inject
    public ShowUsersProjectPermissionAction(PermissionManager permissionManager, UserManager userManager)
    {
        this.permissionManager = permissionManager;
        this.userManager = userManager;
    }

    @Override
    protected void doValidation()
    {
        final UserProfile user = userManager.getRemoteUser();
        //check if a user is logged in, otherwise return with an appropriate error message
        if (user == null)
        {
            addErrorMessage("Please login to view the current user's projects for a permission.");
        }
        final int permissionType = Permissions.getType(permission);
        //then check if the permission provided as a parameter is a valid JIRA permission
        if (permissionType == -1)
        {
            addErrorMessage("Invalid permission specified.  Permission '" + permission + "' is unknown!");
        }
    }

    @Override
    protected String doExecute() throws Exception
    {
        final String username = userManager.getRemoteUser().getUsername();
        final ProjectPermissionKey projectPermissionKey = new ProjectPermissionKey(permission);
        final ApplicationUser applicationUser = ApplicationUsers.byKey(username);
        log.debug("doExecute(): username={}", username);
        //retrieve all the projects that the current user has the given permission type in.
        projects = permissionManager.getProjects(projectPermissionKey, applicationUser, null);
        return SUCCESS;
    }

    public Collection<Project> getProjects()
    {
        return projects;
    }

    public String getPermission()
    {
        return permission;
    }

    public void setPermission(final String permission)
    {
        this.permission = permission;
    }
}
