package org.raboss.dev.atlassian.jira.proman.api;

import net.java.ao.Accessor;
import net.java.ao.Mutator;
import net.java.ao.schema.AutoIncrement;
import net.java.ao.schema.NotNull;
import net.java.ao.schema.PrimaryKey;
import javax.xml.bind.annotation.*;

/**
 * Created by bossekr on 08.03.16.
 * p.407
 */
public interface EvaluationCriterionInterface {
    @AutoIncrement
    @NotNull
    @PrimaryKey("ID")
    Integer getID();
    void setID(Integer id);

    String getName();
    void setName(String name);

    Integer getWeighting();
    void setWeighting(int weighting);

    String getComment();
    void setComment(String comment);

    enum TypeOfIndex {
        INCOMPLETE,
        PLANNING_POKER,
        CHRONOLOGICAL_ORDER,
        PERCENTAGE,
        REFERENCE_QUANTITY,
    }

    @Accessor("TYPEOFINDEX")
    TypeOfIndex getTypeOfIndex();
    @Mutator("TYPEOFINDEX")
    void setTypeOfIndex(TypeOfIndex typeOfIndex);

    @Accessor("BIGNUMBERBETTER")
    Boolean isBigNumberBetter();
    @Mutator("BIGNUMBERBETTER")
    void isBigNumberBetter(Boolean v);

    @Accessor("TYPEOFINDEXDESCRIPTION")
    String getTypeOfIndexDescription();
    @Mutator("TYPEOFINDEXDESCRIPTION")
    void setTypeOfIndexDescription(String typeOfIndexDescription);
}
