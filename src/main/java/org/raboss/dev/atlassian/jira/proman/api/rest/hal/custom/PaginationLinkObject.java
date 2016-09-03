package org.raboss.dev.atlassian.jira.proman.api.rest.hal.custom;

import org.raboss.dev.atlassian.jira.proman.api.rest.LogicalExpression;
import org.raboss.dev.atlassian.jira.proman.api.rest.hal.LinkObject;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;

public class PaginationLinkObject extends LinkObject {
    @XmlElement(name="raboss:offset")
    private Integer offset;

    @XmlElement(name="raboss:limit")
    private Integer limit;

    @XmlElement(name="raboss:count")
    private Integer count;

    @XmlElement(name="raboss:maximum")
    private Integer maximum;

    @XmlElement(name="raboss:orderby")
    private ArrayList<ElementSortInformation> orderby;

    @XmlElement(name="raboss:filter")
    private LogicalExpression filter;

    public PaginationLinkObject setOffset(final Integer offset) {
        this.offset = offset;
        return this;
    }

    public PaginationLinkObject setLimit(final Integer limit) {
        this.limit = limit;
        return this;
    }

    public PaginationLinkObject setCount(final Integer count) {
        this.count = count;
        return this;
    }

    public PaginationLinkObject setMaximum(final Integer maxium) {
        this.maximum = maxium;
        return this;
    }

    public PaginationLinkObject setOrderBy(final ArrayList<ElementSortInformation> orderby) {
        this.orderby = orderby;
        return this;
    }

    public PaginationLinkObject setFilter(final LogicalExpression filter) {
        this.filter = filter;
        return this;
    }
}
