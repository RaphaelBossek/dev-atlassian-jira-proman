package org.raboss.dev.atlassian.jira.proman.api.rest;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Logical Expression
 * <p/>
 * This class represent a logical expression for building s-expression (symbolic expression)
 * for nested list (tree-structured).
 * <p/>
 * This implementation supports {@link http://github.com/jwadhams/json-logic-js}
 * @doc {@link http://en.wikipedia.org/wiki/S-expression}
 * @doc {@link http://github.com/jwadhams/json-logic-js}
 * @doc {@link http://stackoverflow.com/questions/20737045/representing-logic-as-data-in-json}
 */
public class LogicalExpression {
    @XmlElement
    private String key;

    @XmlElement
    private String value;

    @XmlElement
    private List<LogicalExpression> values;

    public enum LogicalOperations {
        AND,
        OR,
        NOT,
        NOR
    }

    public LogicalExpression setOperation(final LogicalOperations op) {
        switch (op) {
            case AND:
                key = "$AND";
                break;
            case OR:
                key = "$OR";
                break;
            case NOT:
                key = "$NOR";
                break;
            case NOR:
                key = "$NOR";
                break;
        }
        return this;
    }

    public LogicalExpression(final String variable, final String value) {
        this.setVariable(variable).setValue(value);
    }

    public LogicalExpression setFirstExpression(final LogicalExpression left) {
        if (this.values == null)
            this.values = new ArrayList<>(2);
        this.values.add(0, left);
        this.resetIfNotLogicalOperand();
        return this;
    }

    public LogicalExpression setNextExpression(final LogicalExpression next) {
        if (this.values == null)
            return this.setFirstExpression(next);
        this.values.add(next);
        this.resetIfNotLogicalOperand();
        return this;
    }

    public LogicalExpression setValue(final String value) {
        this.value = value;
        this.values = null;
        return this;
    }

    public LogicalExpression setVariable(final String variable) {
        this.key = "_" + variable;
        this.values = null;
        return this;
    }

    private void resetIfNotLogicalOperand() {
        if (key != null && key.charAt(0) != '$')
            key = null;
        this.value = null;
    }
}
