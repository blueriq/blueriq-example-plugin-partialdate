package com.blueriq.plugin.partialdate.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.aquima.interactions.foundation.types.DateValue;
import com.aquima.interactions.foundation.types.NumberValue;
import com.aquima.interactions.foundation.types.StringValue;

import org.junit.Test;

public class PartialDateLogicTest {


  @Test
  public void testIncorrectInput() {
    DateValue convertedValue = PartialDateLogic.convertPartialDate(null);
    assertEquals(DateValue.UNKNOWN, convertedValue);

    convertedValue = PartialDateLogic.convertPartialDate(StringValue.UNKNOWN);
    assertEquals(DateValue.UNKNOWN, convertedValue);

    convertedValue = PartialDateLogic.convertPartialDate(new NumberValue(15));
    assertEquals(DateValue.UNKNOWN, convertedValue);

    convertedValue = PartialDateLogic.convertPartialDate(new StringValue(""));
    assertEquals(DateValue.UNKNOWN, convertedValue);
  }

  @Test
  public void testCorrectDates() {
    checkDates("0-11-2018", new DateValue(2018, 11, 1));
    checkDates("0-0-1996", new DateValue(1996, 1, 1));
    checkDates("0-0-0", new DateValue(1000, 1, 1));
    checkDates("00-00-2003", new DateValue(2003, 1, 1));
    checkDates("15-12-2010", new DateValue(2010, 12, 15));
    checkDates("29-2-2012", new DateValue(2012, 2, 29));
  }

  @Test
  public void testIncorrectDates() {
    assertTrue(checkInput("3-3-2011"));

    assertFalse(checkInput(""));
    assertFalse(checkInput("abcdefghijk"));
    assertFalse(checkInput("11-2012"));
    assertFalse(checkInput("1-2-3-4"));
    assertFalse(checkInput("a-b-c"));
    assertFalse(checkInput("15-0-2018"));
    assertFalse(checkInput("0-3-0"));
    assertFalse(checkInput("15-2-0"));
    assertFalse(checkInput("44-12-2003"));
    assertFalse(checkInput("29-2-2005"));
  }

  private void checkDates(String input, DateValue expected) {
    StringValue dateInput = new StringValue(input);
    DateValue convertedDate = PartialDateLogic.convertPartialDate(dateInput);
    assertEquals(expected, convertedDate);
  }

  private boolean checkInput(String input) {
    return PartialDateLogic.isValid(new StringValue(input));
  }
}
