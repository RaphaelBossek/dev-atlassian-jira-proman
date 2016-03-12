package org.raboss.dev.atlassian.jira.proman.entity;

import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import net.java.ao.*;

import java.util.Map;

/**
 * Created by bossekr on 08.03.16.
 * p.407
 */
@Preload
public interface EvalCriterion1 extends Entity {
    public String getName();
    public void setName(String name);

    public int getWeighting();
    public void setWeighting(int weighting);

    public String getDescription();
    public void setDescription(String description);

    public void setIsSmallNumberIsBetter(Boolean v);
    public Boolean isSmallNumberBetter();
    public void setIsBigNumberIsBetter(Boolean v);
    public Boolean isBigNumberBetter();

    public enum TypeOfIndex {
        INCOMPLETE,
        PLANNING_POKER,
        CHRONOLOGICAL_ORDER,
        PERCENTAGE,
        REFERENCE_QUANTITY,
    }

    public void setTypeOfIndex(TypeOfIndex typeOfIndex);
    public TypeOfIndex getTypeOfIndex();

    public void setReferenceQuantityDescription(String referenceQuantityDescription);
    public String getReferenceQuantityDescription();
}
