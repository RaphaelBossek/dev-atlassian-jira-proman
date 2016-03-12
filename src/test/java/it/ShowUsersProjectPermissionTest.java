package it;

import com.atlassian.jira.functest.framework.FuncTestCase;
import com.atlassian.jira.functest.framework.Navigation;
import com.atlassian.jira.functest.framework.locator.IdLocator;
import com.atlassian.jira.functest.framework.locator.TableLocator;
import com.atlassian.jira.functest.framework.locator.WebPageLocator;
import org.junit.Before;
import org.junit.Test;

public class ShowUsersProjectPermissionTest extends FuncTestCase
{

    @Before
    public void setUpTest()
    {
        //restore some test data.  This particular export contains 2 projects and one issue.
        administration.restoreData("TestShowUsersProjectPermission.xml");
    }

    /**
     * This particular test checks that the webwork action included in this plugin provides the correct information and
     * error messages.
     */
    @Test
    public void testUsersProjectPermissions()
    {
        navigation.logout();
        navigation.gotoPage("/secure/ShowUsersProjectPermissionAction.jspa?permission=create");

        //text.assertTextPresent(new WebPageLocator(tester), "Please login to view the current user's projects for a permission.");

        text.assertTextPresent(new WebPageLocator(tester), "You must log in to access this page.");

        navigation.login("admin", "admin");
        navigation.gotoPage("/secure/ShowUsersProjectPermissionAction.jspa?permission=invalidpermission");
        text.assertTextPresent(new WebPageLocator(tester),
                "Invalid permission specified.  Permission 'invalidpermission' is unknown!");

        // https://docs.atlassian.com/jira/6.4.12/com/atlassian/jira/security/Permissions.html
        navigation.gotoPage("/secure/ShowUsersProjectPermissionAction.jspa?permission=ADMINISTER_PROJECTS");
        text.assertTextPresent(new WebPageLocator(tester), "The user admin has the 'ADMINISTER_PROJECTS' permission in the following projects");
        text.assertTextSequence(new IdLocator(tester, "projects-list"), "Homosapien (HSP)", "Monkey (MKY)");
    }

    /**
     * This test does not test anything from this plugin.  It simply uses the func test framework to perform a number
     * of actions and assertions in JIRA for the purposes of this tutorial.
     * It shows how to:
     * <ul>
     * <li>Create a user</li>
     * <li>Create an issue</li>
     * <li>Add a comment to an issue</li>
     * <li>Carry out various assertions</li>
     * </ul>
     */
    @Test
    public void testJiraActions()
    {
        /* first lets create a user */
        administration.usersAndGroups().addUser("fred", "password", "Fred Flintstone", "fred@example.com");

        /* check the user exists.  The previous action would have done this already, but lets check anyway */
        navigation.gotoAdminSection(Navigation.AdminSection.USER_BROWSER);
        //click a link with id fred using jWebUnit
        tester.clickLink("fred");
        text.assertTextSequence(new WebPageLocator(tester),
                "Username:", "fred", "Full Name:", "Fred Flintstone", "Email:", "fred@example.com");
        //also try logging in as that user
        navigation.logout();
        navigation.login("fred", "password");

        navigation.logout();
        navigation.login("admin", "admin");

        /* Lets create a new issue */
        final String issueKey = navigation.issue().createIssue("Homosapien", "Bug", "This is a first bug");
        navigation.issue().viewIssue(issueKey);
        //various ways for asserting stuff.
        // 1. Using text assertions from JIRA's integration test framework using Locators
        //    Locators are awesome.  Very flexible ways to Locate particular parts of your webpage, and very easy to write
        //    yourself
        //text.assertTextPresent(new TableLocator(tester, "issue_header_summary"), "This is a first bug");
        // 2. Using jWebUnit directly
        //tester.assertTextInTable("issue_header_summary", "This is a first bug");
        // 3. Or if you're really desperate by String comparison using the HTML source returned in the response.
        //final String htmlSource = tester.getDialog().getResponseText();
        //assertTrue(htmlSource.contains("This is a first bug"));
        text.assertTextPresent(new IdLocator(tester, "summary-val"), "This is a first bug");

        // 4. Asserting some text is not present
        text.assertTextNotPresent(new WebPageLocator(tester), "Fred Flintstone");

        /* Lets add a comment to an issue and check it was created properly */
        navigation.issue().addComment(issueKey, "I'm not sure this is the bug you're looking for.", null);
        navigation.issue().viewIssue(issueKey);
        text.assertTextPresent(new IdLocator(tester, "issue_actions_container"),
                "I'm not sure this is the bug you're looking for.");
    }
}
