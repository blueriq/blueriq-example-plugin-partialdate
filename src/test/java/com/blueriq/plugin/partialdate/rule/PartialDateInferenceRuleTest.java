package com.blueriq.plugin.partialdate.rule;

import static org.junit.Assert.assertEquals;

import com.aquima.interactions.foundation.DataType;
import com.aquima.interactions.foundation.IValue;
import com.aquima.interactions.foundation.types.DateValue;
import com.aquima.interactions.test.templates.ApplicationTemplate;
import com.aquima.interactions.test.templates.composer.FieldReference;
import com.aquima.interactions.test.templates.composer.PageTemplate;
import com.aquima.interactions.test.templates.model.EntityTemplate;
import com.aquima.interactions.test.templates.model.MetaModelTemplate;
import com.aquima.interactions.test.templates.rules.ExternalRuleTemplate;
import com.aquima.interactions.test.templates.session.PortalSessionTestFacade;
import com.aquima.interactions.test.templates.session.RequestTemplate;

import org.junit.Test;

public class PartialDateInferenceRuleTest {

  @Test
  public void applyPartialDateExternalRule() {
    // Setup

    // create a model with a Person that has a birthdate that can be a partial date
    ApplicationTemplate application = new ApplicationTemplate();
    MetaModelTemplate metaModel = application.getMetaModel();
    EntityTemplate persoon = metaModel.addEntity("Persoon", null, true);
    persoon.addAttribute("Geboortedatum_input", DataType.STRING, false);
    persoon.addAttribute("Geboortedatum", DataType.DATE, false);
    ExternalRuleTemplate externalRuleTemplate = application.getRuleEngine().addExternalSource("Persoon.Geboortedatum");
    application.getRuleEngine().addInferenceRule(new PartialDateInferenceRule(externalRuleTemplate.toDataSource()));

    // put the attributes on a page
    PageTemplate page = application.getComposer().addPage("page");
    FieldReference geboortedatumInputField = page.addField("Persoon.Geboortedatum_input");
    geboortedatumInputField.setIsRefreshField(true);
    FieldReference geboortedatumField = page.addField("Persoon.Geboortedatum");
    geboortedatumField.setReadonly(true);

    // put the page in a flow
    application.getFlowEngine().addFlow("flow").addPage("page");

    // SUT

    // start the flow and input a partial date
    PortalSessionTestFacade testFacade = new PortalSessionTestFacade(application);
    testFacade.startFlow("flow");
    testFacade.handleFieldRefresh("Persoon.Geboortedatum_input",
        new RequestTemplate("Persoon.Geboortedatum_input", "0-0-2003"));

    // Verify

    // the date value should be derived using the external rule
    IValue dateValue = testFacade.getAttributeValue("Persoon", "Geboortedatum");
    assertEquals(dateValue, new DateValue(2003, 1, 1));
  }
}
