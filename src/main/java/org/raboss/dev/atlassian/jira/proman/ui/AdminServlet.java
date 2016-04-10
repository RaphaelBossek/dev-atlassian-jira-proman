package org.raboss.dev.atlassian.jira.proman.ui;

import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.auth.LoginUriProvider;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.user.UserKey;
import com.atlassian.templaterenderer.TemplateRenderer;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Map;

import com.atlassian.sal.api.user.UserManager;

public class AdminServlet extends javax.servlet.http.HttpServlet {
    private static final String PLUGIN_STORAGE_KEY = "org.raboss.dev.com.atlassian.jira.plugin.proman";
    private static final Logger log;
    @ComponentImport
    private final UserManager userManager;
    @ComponentImport
    private final LoginUriProvider loginUriProvider;
    @ComponentImport
    private final TemplateRenderer templateRenderer;
    @ComponentImport
    private final PluginSettingsFactory pluginSettingsFactory;

    static {
        log = LoggerFactory.getLogger(AdminServlet.class);
    }

    @Inject
    public AdminServlet(UserManager userManager, LoginUriProvider loginUriProvider, TemplateRenderer templateRenderer, PluginSettingsFactory pluginSettingsFactory)
    {
        super();
        this.userManager = userManager;
        this.loginUriProvider = loginUriProvider;
        this.templateRenderer = templateRenderer;
        this.pluginSettingsFactory = pluginSettingsFactory;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        log.info("doGet()");
        UserKey userkey = userManager.getRemoteUserKey(request);
        if (userkey == null || !userManager.isSystemAdmin(userkey)) {
            redirectToLogin(request, response);
            return;
        }
        Map<String, Object> context = Maps.newHashMap();

        PluginSettings pluginSettings = pluginSettingsFactory.createGlobalSettings();

        if (pluginSettings.get(PLUGIN_STORAGE_KEY + ".name") == null){
            String noName = "Enter a name here.";
            pluginSettings.put(PLUGIN_STORAGE_KEY +".name", noName);
        }

        if (pluginSettings.get(PLUGIN_STORAGE_KEY + ".age") == null){
            String noAge = "Enter an age here.";
            pluginSettings.put(PLUGIN_STORAGE_KEY + ".age", noAge);
        }

        context.put("name", pluginSettings.get(PLUGIN_STORAGE_KEY + ".name"));
        context.put("age", pluginSettings.get(PLUGIN_STORAGE_KEY + ".age"));
        response.setContentType("text/html;charset=utf-8");
        templateRenderer.render("/templates/admin.vm", response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException
    {
        PluginSettings pluginSettings = pluginSettingsFactory.createGlobalSettings();
        pluginSettings.put(PLUGIN_STORAGE_KEY + ".name", req.getParameter("name"));
        pluginSettings.put(PLUGIN_STORAGE_KEY + ".age", req.getParameter("age"));
        response.sendRedirect("admin");
    }

    private void redirectToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(loginUriProvider.getLoginUri(getUri(request)).toASCIIString());
    }

    private URI getUri(HttpServletRequest request) {
        StringBuffer builder = request.getRequestURL();
        if (request.getQueryString() != null) {
            builder.append("?");
            builder.append(request.getQueryString());
        }
        return URI.create(builder.toString());
    }
}
