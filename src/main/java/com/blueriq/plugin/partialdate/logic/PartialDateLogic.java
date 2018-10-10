package com.blueriq.plugin.partialdate.logic;

import com.aquima.interactions.foundation.IValue;
import com.aquima.interactions.foundation.types.DateValue;
import com.aquima.interactions.foundation.types.StringValue;

public class PartialDateLogic {

  public static final String DATE_SEPARATOR = "-";

  /**
   * Checks whether the supplied value contains a valid partial date. Note that a partial date can contain a regular
   * date, which is also checked for validity.
   * 
   * @param value the value that contains the partial date to validate
   * @return true if the value is a valid partial date, false otherwise
   */
  public static boolean isValid(IValue value) {
    return !convertPartialDate(value).isUnknown();
  }

  /**
   * Converts a partial date value to a regular date. The expected partial date format is the following:
   *
   * <pre>
   * day - month - year
   * </pre>
   *
   * where each part is a positive integer (including zero). Zero signifies that a part is unknown. If the day is
   * non-zero, the date is not partial so the month and year are required to be non-zero either. Similarly, if the month
   * is non-zero, the year is required to be non-zero either.
   *
   * Unknown date parts are converted to a known part so a valid date can be constructed, according to these rules:
   *
   * <pre>
   * day    ->    1
   * month  ->    1
   * year   ->    1000
   * </pre>
   *
   * When all date parts are known (or converted to known), a date is constructed from them and returned.
   *
   * If something goes wrong parsing the date, or the date turns out to be invalid (for instance 44-15-1963), unknown is
   * returned.
   *
   * @param value the input value containing the partial date. This is expected be a StringValue containing a partial
   *        date that conforms to the format. Note that this can be a regular date just as well.
   * @return DateValue the converted date, or unknown if the input could not be converted
   */
  public static DateValue convertPartialDate(IValue value) {
    if (value == null || value.isUnknown() || !(value instanceof StringValue) || value.stringValue().isEmpty()) {
      return DateValue.UNKNOWN;
    }
    String dateInput = value.stringValue();
    String[] parts = dateInput.split(DATE_SEPARATOR);
    if (parts.length == 3) {
      int day;
      int month;
      int year;
      try {
        day = Integer.parseInt(parts[0]);
        month = Integer.parseInt(parts[1]);
        year = Integer.parseInt(parts[2]);
      } catch (NumberFormatException nfe) {
        return DateValue.UNKNOWN;
      }
      // if day is known, this is a full date so the month should also be known
      if (month == 0 && day != 0) {
        return DateValue.UNKNOWN;
      }
      // if month is known the year should also be known
      if (year == 0 && month != 0) {
        return DateValue.UNKNOWN;
      }
      // convert unknown parts to regular date parts
      if (day == 0) {
        day = 1;
      }
      if (month == 0) {
        month = 1;
      }
      if (year == 0) {
        year = 1000;
      }
      try {
        return new DateValue(year, month, day);
      } catch (IllegalArgumentException iae) {
        // the date is not valid
        return DateValue.UNKNOWN;
      }
    }
    return DateValue.UNKNOWN;
  }
}
