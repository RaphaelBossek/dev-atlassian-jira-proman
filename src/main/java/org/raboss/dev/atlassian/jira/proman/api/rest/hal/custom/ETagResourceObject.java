package org.raboss.dev.atlassian.jira.proman.api.rest.hal.custom;

import org.apache.xpath.operations.Bool;

import javax.xml.bind.annotation.XmlElement;

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
 * <p/>
 * @doc {@link http://tools.ietf.org/html/rfc7232#section-2.3}
 */
public class ETagResourceObject {
    @XmlElement(name="otag")
    private String opaqueTag;

    @XmlElement(name="weak")
    private Boolean weak = null;

    public ETagResourceObject setOpaqueTag(final String opaqueTag) {
        this.opaqueTag = opaqueTag;
        return this;
    }

    /**
     * An entity-tag can be either a weak or strong validator, with strong
     * being the default.  If an origin server provides an entity-tag for a
     * representation and the generation of that entity-tag does not satisfy
     * all of the characteristics of a strong validator ({@link http://tools.ietf.org/html/rfc7232#section-2.1}),
     * then the origin server MUST mark the entity-tag as weak.
     */
    public ETagResourceObject isWeak(Boolean weak) {
        this.weak = weak;
        return this;
    }
}
