/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.fudgemsg;

import static org.junit.Assert.assertEquals;

import org.fudgemsg.FudgeMsgField;
import org.fudgemsg.types.StringFieldType;
import org.junit.Test;

import com.opengamma.financial.convention.frequency.Frequency;
import com.opengamma.financial.convention.frequency.SimpleFrequency;

/**
 * Test Frequency Fudge support.
 */
public class FrequencyTest extends FinancialTestBase {

  private static final Frequency s_ref = SimpleFrequency.BIMONTHLY;

  @Test
  public void testCycle() {
    assertEquals(s_ref, cycleObject(Frequency.class, s_ref));
  }

  @Test
  public void testFromString() {
    assertEquals(s_ref, getFudgeContext().getFieldValue(Frequency.class,
        FudgeMsgField.of(StringFieldType.INSTANCE, s_ref.getConventionName())));
  }
}
