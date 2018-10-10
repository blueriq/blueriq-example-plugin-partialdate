package com.blueriq.plugin.partialdate.validator;

import com.aquima.interactions.foundation.IParameters;
import com.aquima.interactions.foundation.IValue;
import com.aquima.interactions.rule.IValidator;

import com.blueriq.component.api.annotation.AquimaValidator;
import com.blueriq.plugin.partialdate.config.PartialDateConfig;
import com.blueriq.plugin.partialdate.logic.PartialDateLogic;

/**
 * A custom validator type for partial dates. This validator type should be registered on a string attribute that is
 * used to input a partial date.
 */
@AquimaValidator(value = PartialDateConfig.PARTIAL_DATE_VALIDATOR)
public class PartialDateValidator implements IValidator {

  @Override
  public String getTypeName() {
    return PartialDateConfig.PARTIAL_DATE_VALIDATOR;
  }

  @Override
  public boolean validate(IValue attrValue, IParameters parameters) {
    // no value is valid
    if (attrValue == null || attrValue.isUnknown() || attrValue.stringValue().isEmpty()) {
      return true;
    }
    return PartialDateLogic.isValid(attrValue);
  }
}
