[![][logo]][website] 

# Partial Date plugin

This is an example plugin that provides a possible solution for partial dates. Partial dates are dates from which a part is unknown: either the day, day and month, or day and month and year.

The missing parts in a partial date are substituted with a 0, so these are some examples of partial dates:

0-11-2012

0-0-1980

0-0-0

With a partial date you can express that someone was born in 1960, without knowing the exact month and day. As you can imagine, once you have to determine the age of that person, you still need to convert the partial date to a regular date to perform calculations on. That is what this plugin does. Next to that, it also checks that partial dates are valid, because when for instance the day of a date is known, it is actually a regular date so the month cannot be unknown.

The plugin contains two custom components that extend Blueriq to handle this problem: an *external rule* and a *validation type*. These are standard extension points in Blueriq for which you can write custom code to solve problems that Blueriq currently cannot handle. 

# Plugin contents

### External rule

This plugin contains an external rule that derives an actual date from the partial date, following really simple rules:
```
if input_day = 0 -> day = 1
if input_month = 0 -> month = 1
if input_year = 0 -> year = 1000
```

The external rule is coupled to a date attribute as default in the Studio model. It takes the value from the partial date input, tries to parse it as a date using the simple rules and returns a date if it succeeds. 

### Validation type

Since the partial date input is a simple string attribute, you can also input invalid (partial) dates. To cover this, next to the external rule the plugin contains an external validation type that checks the input (following the same basic rules). This validation type is coupled to the input attribute in the studio model.

### Studio model

In the model in Studio, two attributes need to be present for a partial date:
1. a string attribute to input the partial date
2. a date attribute that is derived from the partial date to perform date calculations with

The partial date input and the derived date are coupled by naming convention (the partial date input has the same name as the data attribute suffixed with `_input`).

# Build from source

To compile and build war use:

```
mvn clean verify -DskipTests
```

To test the war, please add the Blueriq `license.aql` to `src\test\resources` and use:

```
mvn clean verify
```  

# Run example

Deploy `Runtime.war` to Tomcat container. Create a configuration folder and add Blueriq `license.aql` or package Blueriq `license.aql` by adding it to `src\main\resources`.

Optionally, you can use the example project *PartialDates* which you can find in the `src/test/resources` folder. It contains a basic page with two partial date attributes that demonstrate the functionality.

Start Tomcat container with the following parameters:

```bash
-Dspring.config.additional-location=file://path_to_conf/ # URI of the configuration folder which contains the Blueriq license.
-Dspring.profiles.active=native,development-tools
```

# Blueriq version

This plugin was built on Blueriq 11 (using the project starter).

[logo]: https://www.blueriq.com/Static/images/logo_gradient.svg
[website]: http://www.blueriq.com
