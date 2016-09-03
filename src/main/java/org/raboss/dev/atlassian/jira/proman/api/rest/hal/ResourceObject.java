package org.raboss.dev.atlassian.jira.proman.api.rest.hal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

/**
 * This is a simple and straight forward approach for
 * JSON Hypertext Application Language (HAL) as defined in
 * {@link http://datatracker.ietf.org/doc/draft-kelly-json-hal}
 * for expressing links between resources in JSON. We have
 * only implemented those elements which are mandatory for this
 * application.
 * <p/>
 * A ResourceObject represents a resource. It has two reserved properties:
 * <ul>
 * <ol><pre>_links</pre>: contains links to other resources.</ol>
 * <ol><pre>_embedded</pre>: contains embedded resources.</ol>
 * </ul>
 * All other properties MUST be valid JSON, and represent the current state of the resource.
 * <p/>
 *
 * @doc {@link http://tools.ietf.org/html/draft-kelly-json-hal-08#section-4}
 */
public class ResourceObject<T extends LinkRelation> {
    /**
     * The reserved <pre>_links</pre> property is OPTIONAL.
     * <p/>
     * It is an object whose property names are <pre>LinkRelation</pre> types (as
     * defined by {@link http://tools.ietf.org/html/rfc5988#section-5.3})
     * and values are either a <code>LinkRelation</code> or an array
     * of <code>LinkObjects</code>.  The subject resource of these links is the
     * <code>ResourceObject</code> of which the containing <pre>_links</pre> object is a property.
     * @doc {@link http://tools.ietf.org/html/draft-kelly-json-hal-08#section-4.1.1}
     */
    @XmlElement(name="_links")
    private T linkRelation;

    /**
     * The reserved "_embedded" property is OPTIONAL
     * <p/>
     * It is an object whose property names are link relation types (as
     * defined by {@link http://tools.ietf.org/html/rfc5988#section-5.3}
     * and values are either a <code>ResourceObject</code> or an array of <code>ResourceObjects</code>.
     * <p/>
     * Embedded Resources MAY be a full, partial, or inconsistent version of
     * the representation served from the target URI.
     * @doc {@link http://tools.ietf.org/html/draft-kelly-json-hal-08#section-4.1.2}
     */
    @XmlElement(name="_embedded")
    private Map<String,? extends ResourceObject> resourceObjects;

    public ResourceObject setLinkRelation(final T linkRelation) {
        this.linkRelation = linkRelation;
        return this;
    }

    public ResourceObject setResourceObjects(Map<String,? extends ResourceObject> resourceObjects) {
        this.resourceObjects = resourceObjects;
        return this;
    }
}
