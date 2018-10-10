package com.blueriq.plugin.partialdate.rule;

import com.aquima.interactions.foundation.DataType;
import com.aquima.interactions.foundation.GUID;
import com.aquima.interactions.foundation.IValue;
import com.aquima.interactions.foundation.types.DateValue;
import com.aquima.interactions.profile.IEntityInstance;
import com.aquima.interactions.profile.SourceType;
import com.aquima.interactions.rule.IInferenceRule;
import com.aquima.interactions.rule.IRuleListener;
import com.aquima.interactions.rule.InferenceContext;
import com.aquima.interactions.rule.RuleEngineException;
import com.aquima.interactions.rule.SourcedValue;
import com.aquima.interactions.rule.ValueType;
import com.aquima.interactions.rule.ds.IExternalRuleDS;

import com.blueriq.plugin.partialdate.logic.PartialDateLogic;

/**
 * External rule that converts a partial date to a regular date. This external rule should be the default value of a
 * date attribute (the target attribute). It expects a string attribute on the same entity with the same name as the
 * target attribute plus a suffix that contains the partial date.
 */
class PartialDateInferenceRule implements IInferenceRule {

  private static final String INPUT_SUFFIX = "_input";
  private final IExternalRuleDS definition;

  public PartialDateInferenceRule(IExternalRuleDS definition) {
    this.definition = definition;
  }

  @Override
  public SourceType getSourceType() {
    return SourceType.RULE;
  }

  @Override
  public SourcedValue[] evaluateUsing(InferenceContext context, IRuleListener listener) throws RuleEngineException {
    try {
      String targetAttribute = getTargetAttributes()[0];
      String entity = targetAttribute.substring(0, targetAttribute.indexOf('.'));
      IEntityInstance instance = context.getActiveInstance(entity);
      IValue inputValue = instance.getValue(targetAttribute + INPUT_SUFFIX);
      if (PartialDateLogic.isValid(inputValue)) {
        DateValue dateValue = PartialDateLogic.convertPartialDate(inputValue);
        return new SourcedValue[] { new SourcedValue(targetAttribute, dateValue) };
      } else {
        return new SourcedValue[0];
      }
    } catch (Exception ex) {
      throw new RuleEngineException("Exception during evaluating partial date external rule", ex);
    }
  }

  @Override
  public ValueType getValueType(String attribute) {
    return new ValueType(DataType.DATE, false);
  }

  @Override
  public String getJustificationTextId(String attribute, String justificationId) {
    return null;
  }

  @Override
  public GUID getId() {
    return GUID.generate();
  }

  @Override
  public String getName() {
    return definition.getName();
  }

  @Override
  public String[] getSourceScopeObjects() {
    return new String[] {};
  }

  @Override
  public String[] getTargetAttributes() {
    return definition.getTargetAttributes();
  }

  @Override
  public String[] getRuleGroupNames() {
    return definition.getRuleGroupNames();
  }
}
