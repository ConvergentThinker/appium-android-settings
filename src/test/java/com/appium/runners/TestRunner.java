package com.appium.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * JUnit 4 Cucumber test runner.
 *
 * Run all tests : mvn test
 * Run by tag    : mvn test -Dcucumber.filter.tags="@smoke"
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        // ── Feature files location ────────────────────────────────────────────
        features = "src/test/resources/features",

        // ── Glue: step definitions + hooks ───────────────────────────────────
        glue = {
                "com.appium.steps",
                "com.appium.hooks"
        },

        // ── Reporting plugins ─────────────────────────────────────────────────
        plugin = {
                "pretty",                                        // console output
                "html:target/cucumber-reports/report.html",     // HTML report
                "json:target/cucumber-reports/report.json",     // JSON  report
                "junit:target/cucumber-reports/report.xml"      // JUnit XML report
        },

        // ── Show every step in console ────────────────────────────────────────
        monochrome = true,

        // ── Tags (override via -Dcucumber.filter.tags on CLI) ─────────────────
        tags = "@smoke1"
)
public class TestRunner {
    // This class is intentionally empty.
    // All configuration is done through @CucumberOptions above.
}
