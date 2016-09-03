package org.raboss.dev.atlassian.jira.proman.api.rest.hal.custom;

import javax.xml.bind.annotation.XmlElement;

public class ElementSortInformation {
    @XmlElement
    private String key;

    public enum TypeOfSortOrder {
        ASC,
        DESC,
        UNKNOWN
    }
    @XmlElement
    private TypeOfSortOrder order = TypeOfSortOrder.UNKNOWN;

    public ElementSortInformation setKey(final String key) {
        this.key = key;
        return this;
    }

    public ElementSortInformation setSortOrder(final TypeOfSortOrder order) {
        this.order = order;
        return this;
    }
}
