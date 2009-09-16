/**
 * Copyright (C) 2009 - 2009 by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.engine.analytics;

import java.math.BigDecimal;

// REVIEW kirk 2009-09-15 -- Should this be a generic? I thought about it,
// but it seemed an awful lot of work for very little gain.
// REVIEW jim 2009-09-16 -- we think it should be (the royal we).

/**
 * Represents a particular value which is produced as a result of invoking
 * an {@link AnalyticFunction} over a set of inputs.
 *
 * @author jim
 */
public interface AnalyticValue {
  AnalyticValueDefinition getDefinition();
  Object getValue();
  /**
   * Return a copy of this value scaled by the quantity provided.
   * This is used to convert per-unit results into a number appropriate
   * for a position in a security.
   * 
   * @param quantity The scaling of the underlying in question.
   * @return A copy of this value, scaled by the according precision.
   */
  AnalyticValue scaleForPosition(BigDecimal quantity);
}
