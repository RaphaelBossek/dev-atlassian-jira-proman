package org.raboss.dev.atlassian.jira.proman.entity;

import net.java.ao.RawEntity;
import org.raboss.dev.atlassian.jira.proman.api.EvaluationCriterionInterface;

/**
 * This class is only mandatory for the ActiveObjects representation
 * of the persistent POJO.
 * @see {@link http://answers.atlassian.com/questions/119357/answers/3346375}
 */
public interface EvaluationCriterion extends EvaluationCriterionInterface, RawEntity<Integer> {
}
