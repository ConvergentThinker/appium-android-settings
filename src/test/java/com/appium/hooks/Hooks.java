package com.appium.hooks;

import com.appium.utils.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cucumber Hooks – runs before / after every scenario.
 */
public class Hooks {

    private static final Logger log = LoggerFactory.getLogger(Hooks.class);

    @Before(order = 0)
    public void setUp(Scenario scenario) {
        log.info("▶  Starting scenario: [{}]", scenario.getName());
        DriverManager.initDriver();
    }

    @After(order = 0)
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            log.error("✗  Scenario FAILED: [{}]", scenario.getName());
            // Optionally capture a screenshot here:
            // byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver())
            //         .getScreenshotAs(OutputType.BYTES);
            // scenario.attach(screenshot, "image/png", "Screenshot on failure");
        } else {
            log.info("✔  Scenario PASSED: [{}]", scenario.getName());
        }
        DriverManager.quitDriver();
    }
}
