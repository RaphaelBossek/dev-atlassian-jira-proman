package org.raboss.dev.atlassian.jira.proman.api.rest.hal.custom;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;

/**
 * The "raboss:_etag" in a response provides the current entity-tag
 * for the selected representation, as determined at the conclusion of
 * handling the request.  An entity-tag is an opaque validator for
 * differentiating between multiple representations of the same
 * resource, regardless of whether those multiple representations are
 * due to resource state changes over time, content negotiation
 * resulting in multiple representations being valid at the same time,
 * or both.
 * <p/>
 * An entity-tag can be more reliable for validation than a modification
 * date in situations where it is inconvenient to store modification
 * dates, where the one-second resolution of HTTP date values is not
 * sufficient, or where modification dates are not consistently
 * maintained.
 * @doc {@link http://tools.ietf.org/html/rfc7232#section-2.3}
 */
public class ETag {
    @XmlElement(name="raboss:_etag")
    private ETagResourceObject etagResource;

    @XmlElement(name="raboss:_if-match")
    private ArrayList<String> etagIfMatch;

    public ETag setETagResource(final ETagResourceObject etagResource) {
        this.etagResource = etagResource;
        return this;
    }

    /**
     * The "_if-match" field makes the request method conditional on
     * the recipient origin server either having at least one current
     * representation of the target resource, when the field-value is "*",
     * or having a current representation of the target resource that has an
     * entity-tag matching a member of the list of entity-tags provided in
     * the field-value.
     * <p/>
     * An origin server MUST use the strong comparison function when
     * comparing entity-tags for If-Match ({@link http://tools.ietf.org/html/rfc7232#section-2.3.2}),
     * since the client intends this precondition to prevent the method
     * from being applied if there have been any changes to the representation
     * data.
     * <p/>
     * "_if-match" is most often used with state-changing methods (e.g., POST,
     * PUT, DELETE) to prevent accidental overwrites when multiple user
     * agents might be acting in parallel on the same resource (i.e., to
     * prevent the "lost update" problem).  It can also be used with safe
     * methods to abort a request if the selected representation does not
     * match one already stored (or partially stored) from a prior request.
     * <p/>
     * An origin server that receives an "_if-match" field MUST evaluate
     * the condition prior to performing the method ({@link http://tools.ietf.org/html/rfc7232#section-5}).
     * If the field-value is "*", the condition is false if the origin server does
     * not have a current representation for the target resource.  If the
     * field-value is a list of entity-tags, the condition is false if none
     * of the listed tags match the entity-tag of the selected
     * representation.
     * <p/>
     * An origin server MUST NOT perform the requested method if a received
     * "_if-match" condition evaluates to false; instead, the origin server
     * MUST respond with either a) the 412 (Precondition Failed) status code
     * or b) one of the 2xx (Successful) status codes if the origin server
     * has verified that a state change is being requested and the final
     * state is already reflected in the current state of the target
     * resource (i.e., the change requested by the user agent has already
     * succeeded, but the user agent might not be aware of it, perhaps
     * because the prior response was lost or a compatible change was made
     * by some other user agent).  In the latter case, the origin server
     * MUST NOT send a validator header field in the response unless it can
     * verify that the request is a duplicate of an immediately prior change
     * made by the same user agent.
     * <p/>
     * The "_if-match" field can be ignored by caches and intermediaries
     * because it is not applicable to a stored response.
     * @param etagIfMatch List of opaque etags
     * @return this
     */
    public ETag setETagIfMatch(final ArrayList<String> etagIfMatch) {
        this.etagIfMatch = etagIfMatch;
        return this;
    }
}
