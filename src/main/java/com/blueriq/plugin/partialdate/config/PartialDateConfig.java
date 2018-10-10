package com.blueriq.plugin.partialdate.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "com.blueriq.plugin.partialdate.rule", "com.blueriq.plugin.partialdate.validator" })
public class PartialDateConfig {

  public static final String PARTIAL_DATE_RULE = "PartialDate";
  public static final String PARTIAL_DATE_VALIDATOR = "PartialDate";

}
