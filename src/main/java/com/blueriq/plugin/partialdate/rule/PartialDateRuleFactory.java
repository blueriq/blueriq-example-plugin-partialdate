package com.blueriq.plugin.partialdate.rule;

import com.aquima.interactions.foundation.connectivity.IConnectionManager;
import com.aquima.interactions.foundation.report.InitializationException;
import com.aquima.interactions.metamodel.IMetaModel;
import com.aquima.interactions.project.IProject;
import com.aquima.interactions.rule.IConstraint;
import com.aquima.interactions.rule.IDataRuleDefinition;
import com.aquima.interactions.rule.IExpressionParser;
import com.aquima.interactions.rule.IInferenceRule;
import com.aquima.interactions.rule.IRuleFactory;
import com.aquima.interactions.rule.RuleEngineException;
import com.aquima.interactions.rule.ds.IExternalRuleDS;

import com.blueriq.component.api.annotation.AquimaRuleFactory;
import com.blueriq.plugin.partialdate.config.PartialDateConfig;

@AquimaRuleFactory
public class PartialDateRuleFactory implements IRuleFactory {

  @Override
  public IConstraint getConstraintRule(IExternalRuleDS definition, IMetaModel model, IExpressionParser parser)
      throws RuleEngineException {
    return null;
  }

  @Override
  public IInferenceRule getInferenceRule(IExternalRuleDS definition, IMetaModel model, IExpressionParser parser)
      throws RuleEngineException {
    if (definition.getName().equals(PartialDateConfig.PARTIAL_DATE_RULE)) {
      return new PartialDateInferenceRule(definition);
    }
    return null;
  }

  @Override
  public IInferenceRule getDataRule(IDataRuleDefinition ruleDefinition, IMetaModel model, IProject project,
      IExpressionParser parser, IConnectionManager connectionManager) throws InitializationException {
    return null;
  }
}


