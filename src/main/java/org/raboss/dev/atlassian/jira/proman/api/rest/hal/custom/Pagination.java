package org.raboss.dev.atlassian.jira.proman.api.rest.hal.custom;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;

/**
 * Defines a custom link relation for pagination.
 */
public class Pagination {
    @XmlElement(name="raboss:pagination")
    ArrayList<PaginationLinkObject> pages;

    public Pagination setPages(final ArrayList<PaginationLinkObject> pages) {
        this.pages = pages;
        return this;
    }
}
