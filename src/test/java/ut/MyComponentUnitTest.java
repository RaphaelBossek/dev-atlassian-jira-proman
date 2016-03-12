package ut;

import org.junit.Ignore;
import org.junit.Test;
import org.raboss.dev.atlassian.jira.proman.api.MyPluginComponent;
import org.raboss.dev.atlassian.jira.proman.impl.MyPluginComponentImpl;

import static org.junit.Assert.assertEquals;

public class MyComponentUnitTest
{
    @Test
    public void testMyName()
    {
        MyPluginComponent component = new MyPluginComponentImpl(null);
        assertEquals("names do not match!", "myComponent",component.getName());
    }

    @Ignore
    @Test
    public void testAdd()
    {
        MyPluginComponent component = new MyPluginComponentImpl(null);
        assertEquals("Result", 8, component.addNumbers(8, 8));
    }
}
