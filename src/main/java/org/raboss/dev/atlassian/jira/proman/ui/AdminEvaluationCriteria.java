package org.raboss.dev.atlassian.jira.proman.ui;

import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.templaterenderer.TemplateRenderer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by bossekr on 06.03.16.
 */
@Scanned
public class AdminEvaluationCriteria extends javax.servlet.http.HttpServlet {
    @ComponentImport
    private TemplateRenderer templateRenderer;

    public AdminEvaluationCriteria(TemplateRenderer templateRenderer) {
        this.templateRenderer = templateRenderer;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        templateRenderer.render("/templates/AdminEvaluationCriteria.vm", resp.getWriter());
    }
}
