package com.scm.automation.test;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * CucumberRunnerTest class.
 *
 * @author vipin.v
 * @version 1.0.0
 * @since 1.0.0
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty", "com.epam.reportportal.cucumber.ScenarioReporter"},
    features = {"src/test/resources/test.feature"},
    glue = {"com/scm/automation/test/cucumber"})
public class CucumberRunnerTest {}
