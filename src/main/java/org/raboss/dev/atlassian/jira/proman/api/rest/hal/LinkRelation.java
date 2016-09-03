package org.raboss.dev.atlassian.jira.proman.api.rest.hal;

import org.codehaus.jackson.annotate.JsonUnwrapped;

import javax.xml.bind.annotation.XmlElement;
import java.util.*;
import java.util.HashMap;
import java.util.Map;

/**
 * This is a simple and straight forward approach for
 * JSON Hypertext Application Language (HAL) as defined in
 * {@link http://datatracker.ietf.org/doc/draft-kelly-json-hal}
 * for expressing links between resources in JSON. We have
 * only implemented those elements which are mandatory for this
 * application.
 * <p/>
 * An alternative approach would be the "300 Multiple choices"
 * HTTP status code as described in Hypertext Transfer Protocol (HTTP/1.1):
 * Semantics and Content, 300 Multiple Choices {@link http://tools.ietf.org/html/rfc7231#section-6.4.1}
 * using Web linking Relation Type {@link http://tools.ietf.org/html/rfc5988#section-5.3}
 * <p/>
 * JSON HAL uses by the way the same rel-URI as described in {@link http://tools.ietf.org/html/rfc5988#section-5.3}:
 * <ul>
 * <li>&lt;IRI&gt;;rel="start": How to receiving the first elements</li>
 * <li>&lt;IRI&gt;;rel="previous": How to get the previous elements</li>
 * <li>&lt;IRI&gt;;rel="next": Where to get the next set of elements</li>
 * </ul>
 * <p/>
 * It is an object whose property names are link relation types (as
 * defined by {@link http://tools.ietf.org/html/rfc5988#section-4}) and
 * values are either a <pre>LinkObject</pre> or an array
 * of <pre>LinkObjects</pre>. The subject resource of these links is the <pre>ResourceObject</pre>
 * of which the containing <pre>_links</pre> object is a property.
 */
public class LinkRelation {
    @XmlElement
    private LinkObject self, start, next, previous, last;

    /**
     * Set the Registered Link Relation Type: <pre>self</pre>
     * @param self <pre>LinkObject</pre> representing the <pre>self</pre> Link Relation Type
     * @return The <pre>this</pre> instance
     * @doc {@link http://tools.ietf.org/html/rfc5988#section-6.2.2}
     * @doc {@link http://www.iana.org/assignments/link-relations/link-relations.xhtml}
     */
    public LinkRelation setSelfLinkRelation(final LinkObject self) {
        this.self = self;
        return this;
    }

    /**
     * Set the Registered Link Relation Type: <pre>start</pre>
     * @param start <pre>LinkObject</pre> representing the <pre>start</pre> Link Relation Type
     * @return The <pre>this</pre> instance
     * @doc {@link http://tools.ietf.org/html/rfc5988#section-6.2.2}
     * @doc {@link http://www.iana.org/assignments/link-relations/link-relations.xhtml}
     */
    public LinkRelation setStartLinkRelation(final LinkObject start) {
        this.start = start;
        return this;
    }

    /**
     * Set the Registered Link Relation Type: <pre>next</pre>
     * @param next <pre>LinkObject</pre> representing the <pre>next</pre> Link Relation Type
     * @return The <pre>this</pre> instance
     * @doc {@link http://tools.ietf.org/html/rfc5988#section-6.2.2}
     * @doc {@link http://www.iana.org/assignments/link-relations/link-relations.xhtml}
     */
    public LinkRelation setNextLinkRelation(final LinkObject next) {
        this.next = next;
        return this;
    }

    /**
     * Set the Registered Link Relation Type: <pre>previous</pre>
     * @param previous <pre>LinkObject</pre> representing the <pre>previous</pre> Link Relation Type
     * @return The <pre>this</pre> instance
     * @doc {@link http://tools.ietf.org/html/rfc5988#section-6.2.2}
     * @doc {@link http://www.iana.org/assignments/link-relations/link-relations.xhtml}
     */
    public LinkRelation setPreviousLinkRelation(final LinkObject previous) {
        this.previous = previous;
        return this;
    }

    /**
     * Set the Registered Link Relation Type: <pre>last</pre>
     * @param last <pre>LinkObject</pre> representing the <pre>previous</pre> Link Relation Type
     * @return The <pre>this</pre> instance
     * @doc {@link http://tools.ietf.org/html/rfc5988#section-6.2.2}
     * @doc {@link http://www.iana.org/assignments/link-relations/link-relations.xhtml}
     */
    public LinkRelation setLastLinkRelation(final LinkObject last) {
        this.last = last;
        return this;
    }
}
