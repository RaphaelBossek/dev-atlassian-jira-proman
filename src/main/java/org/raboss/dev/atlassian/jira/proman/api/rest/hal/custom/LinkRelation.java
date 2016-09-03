package org.raboss.dev.atlassian.jira.proman.api.rest.hal.custom;

import org.raboss.dev.atlassian.jira.proman.api.rest.hal.LinkObject;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;

/**
 * Applications that don't wish to register a link relation type can use an
 * extension relation type, which is a URI {@link http://tools.ietf.org/html/rfc3986}
 * that uniquely identifies the relation type. Although the URI can point to a
 * resource that contains a definition of the semantics of the relation
 * type, clients SHOULD NOT automatically access that resource to avoid
 * overburdening its server.
 * @doc {@link http://tools.ietf.org/html/rfc5988#section-4.2}
 */
public class LinkRelation extends org.raboss.dev.atlassian.jira.proman.api.rest.hal.LinkRelation {
    @XmlElement
    private ArrayList<LinkObject> curies;

    /**
     * Custom link relation types (Extension Relation Types in {@link http://tools.ietf.org/html/rfc5988#section-4.2})
     * SHOULD be URIs that when dereferenced in a web browser provide
     * relevant documentation, in the form of an HTML page, about the
     * meaning and/or behaviour of the target Resource.  This will improve
     * the discoverability of the API.
     * @param curies Custom Link Relations
     * @doc {@link http://tools.ietf.org/html/draft-kelly-json-hal-08#section-8.2}
     */
    public LinkRelation setCurieLinkRelations(final ArrayList<LinkObject> curies) {
        this.curies = curies;
        return this;
    }

    public ArrayList<LinkObject> getCurieLinkRelations() {
        return curies;
    }
}
