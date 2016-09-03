package org.raboss.dev.atlassian.jira.proman.api.rest.hal;

import javax.xml.bind.annotation.XmlElement;

/**
 * A <pre>LinkObject</pre> represents a hyperlink from the containing resource to a URI.
 * <p/>
 * @doc {@link http://tools.ietf.org/html/draft-kelly-json-hal-08#section-5}
 */
public class LinkObject {
    @XmlElement
    private String href = new String(), name, title;

    @XmlElement
    private Boolean templated;

    /**
     * The <pre>href</pre> property is REQUIRED.
     * <p/>
     * Its value is either a URI {@link http://tools.ietf.org/html/rfc3986} or a URI Template {@link http://tools.ietf.org/html/rfc6570}.
     * <p/>
     * If the value is a URI Template then the Link Object SHOULD have a "templated" attribute whose value is true.
     * @param href URI
     * @return The <pre>this</pre> instance
     * @doc {@link http://tools.ietf.org/html/draft-kelly-json-hal-08#section-5.1}
     */
    public LinkObject setHref(final String href) {
        this.href = href;
        return this;
    }

    /**
     * The <pre>templated</pre> property is OPTIONAL.
     * <p/>
     * Its value SHOULD be considered false if it is undefined or any other value than true.
     * @param enabled Its value is boolean and SHOULD be true when the <pre>LinkObject</pre>'s <pre>href</pre> property is a URI Template.
     * @return Thie <pre>this</pre> instance
     * @doc {@link http://tools.ietf.org/html/draft-kelly-json-hal-08#section-5.2}
     */
    public LinkObject setTemplated(final Boolean enabled) {
        this.templated = enabled;
        return this;
    }

    /**
     * The <pre>name</pre> property is OPTIONAL.
     * <p/>
     * Its value MAY be used as a secondary key for selecting <pre>LinkObjects</pre> which share the same relation type.
     * @param name Identifies the <pre>LinkObject</pre> by name
     * @return Thie <pre>this</pre> instance
     * @doc {@link http://tools.ietf.org/html/draft-kelly-json-hal-08#section-5.5}
     */
    public LinkObject setName(final String name) {
        this.name = name;
        return this;
    }

    /**
     * The <pre>title</pre> property is OPTIONAL.
     * <p/>
     * Its value is a string and is intended for labelling the link with a human-readable identifier (as defined by {@link http://tools.ietf.org/html/rfc5988#section-5.4}).
     * <p/>
     * The <pre>title</pre> parameter, when present, is used to label the destination
     * of a link such that it can be used as a human-readable identifier
     * (e.g., a menu entry) in the language indicated by the <pre>Content-Language</pre> header (if present).
     * <p/>
     * The "title" parameter MUST NOT appear more than once in a given link-value; occurrences after the first
     * MUST be ignored by parsers.
     * @param title String to label the destination in human-readable manner
     * @return Thie <pre>this</pre> instance
     * @doc {@link http://tools.ietf.org/html/draft-kelly-json-hal-08#section-5.7}
     */
    public LinkObject setTitle(final String title) {
        this.title = title;
        return this;
    }
}
