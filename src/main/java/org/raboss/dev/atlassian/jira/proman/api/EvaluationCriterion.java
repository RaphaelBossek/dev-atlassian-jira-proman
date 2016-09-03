package org.raboss.dev.atlassian.jira.proman.api;

import org.codehaus.jackson.annotate.JsonUnwrapped;
import org.raboss.dev.atlassian.jira.proman.api.rest.hal.ResourceObject;
import org.raboss.dev.atlassian.jira.proman.api.rest.hal.custom.ETag;
import org.raboss.dev.atlassian.jira.proman.api.rest.hal.custom.ETagResourceObject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
//import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Representation of the <code>EvaluationCriterionInterface</code> in order to
 * be serialised with <pre>Jackson</pre>.
 * @see {@link http://answers.atlassian.com/questions/119357/answers/3346375}
 */
@XmlRootElement(name="evaluationcriterion")
@XmlAccessorType(XmlAccessType.NONE)
public class EvaluationCriterion extends ResourceObject implements EvaluationCriterionInterface {
    private Integer id;
    @XmlElement
    private String name;
    @XmlElement
    private Integer weighting;
    @XmlElement
    private String comment;
    @XmlElement
    private EvaluationCriterionInterface.TypeOfIndex typeofindex;
    @XmlElement
    private Boolean bignumberbetter;
    @XmlElement
    private String typeofindexdescription;
    @JsonUnwrapped
    @XmlElement
    private ETag etag;

    public EvaluationCriterion() {
        super();
        setETag(null);
        setID(-1);
        setName("");
        setComment("");
        setWeighting(100);
        setTypeOfIndex(TypeOfIndex.PERCENTAGE);
        setTypeOfIndexDescription("");
        isBigNumberBetter(true);
    }
    public EvaluationCriterion(EvaluationCriterionInterface ec) {
        super();
        copy(ec, this);
    }

    @Override
    public Integer getID() {
        return this.id;
    }

    @Override
    public void setID(Integer id) { this.id = id; }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getWeighting() {
        return this.weighting;
    }

    @Override
    public void setWeighting(int weighting) {
        this.weighting = weighting;
    }

    @Override
    public String getComment() {
        return this.comment;
    }

    @Override
    public void setComment(String comment) {
        this.comment = comment == null ? "" : comment;
    }

    @Override
    public TypeOfIndex getTypeOfIndex() {
        return this.typeofindex;
    }

    @Override
    public void setTypeOfIndex(TypeOfIndex typeOfIndex) {
        this.typeofindex = typeOfIndex;
    }

    @Override
    public Boolean isBigNumberBetter() {
        return this.bignumberbetter;
    }

    @Override
    public void isBigNumberBetter(Boolean v) {
        this.bignumberbetter = v;
    }

    @Override
    public String getTypeOfIndexDescription() {
        return this.typeofindexdescription;
    }

    @Override
    public void setTypeOfIndexDescription(String typeOfIndexDescription) {
        this.typeofindexdescription = typeOfIndexDescription == null ? "" : typeOfIndexDescription;
    }

    /**
     * Set all values 1:1 from source to target
     * @param source Values to be copied from
     * @param target Values to be stored in
     * @return target instance after a copy
     */
    static public EvaluationCriterionInterface copy(final EvaluationCriterionInterface source, EvaluationCriterionInterface target) {
        target.setID(source.getID());
        return set(source, target);
    }

    /**
     * Set only the data from source to target, without ID
     * @param source Values to be copied from
     * @param target Values to be stored in
     * @return target instance after operation
     */
    static public EvaluationCriterionInterface set(final EvaluationCriterionInterface source, EvaluationCriterionInterface target) {
        target.setName(source.getName());
        target.setWeighting(source.getWeighting());
        target.setComment(source.getComment());
        target.setTypeOfIndex(source.getTypeOfIndex());
        target.isBigNumberBetter(source.isBigNumberBetter());
        target.setTypeOfIndexDescription(source.getTypeOfIndexDescription());
        return target;
    }

    /**
     * Hashcode calculation for selected properties. ID is not considered.
     * @return hashcode for this POJO
     */
    public int hashCode() {
        Integer hashcode = 1;
        Integer[] hcs = new Integer[]{this.name.hashCode(),this.weighting.hashCode(),this.comment.hashCode(),this.typeofindex.hashCode(),this.bignumberbetter.hashCode(),this.typeofindexdescription.hashCode()};
        for (Integer hc : hcs) {
            hashcode = hashcode * 31 + hc;
        }
        return hashcode;
    }

    public EvaluationCriterion setETag(ETag etag) {
        this.etag = etag;
        return this;
    }

    public EvaluationCriterion setETagResource(final ETagResourceObject etag) {
        this.etag = new ETag(){{
            setETagResource(etag);}};
        return this;
    }
}
