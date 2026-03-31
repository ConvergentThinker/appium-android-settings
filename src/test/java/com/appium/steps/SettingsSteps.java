package com.appium.steps;

import com.appium.pages.SettingsPage;
import com.appium.utils.DriverManager;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Step definitions for the Android Settings feature.
 */
public class SettingsSteps {

    private static final Logger log = LoggerFactory.getLogger(SettingsSteps.class);

    private AndroidDriver driver() {
        return DriverManager.getDriver();
    }

    public WebDriverWait waitelement() {
        return DriverManager.getWait();
    }

    private SettingsPage settingsPage() {
        return new SettingsPage(driver(), waitelement());
    }

    // ─── Given ───────────────────────────────────────────────────────────────

    @Given("the Android Settings app is open")
    public void theAndroidSettingsAppIsOpen() {
        log.info("Step → the Android Settings app is open");
        settingsPage().waitForHome();
        log.info("Settings main screen is ready");
    }

    // ─── Then ─────────────────────────────────────────────────────────────────

    @Then("the Settings app should be displayed")
    public void theSettingsAppShouldBeDisplayed() {
        log.info("Step → the Settings app should be displayed");
        boolean isVisible = settingsPage().isHomeDisplayed();
        assertTrue("Settings main screen is not visible", isVisible);
        log.info("Verified: Settings main screen is displayed");
    }

    @And("the app title should be visible")
    public void theAppTitleShouldBeVisible() {
        log.info("Step → the app title should be visible");
        boolean titleFound = settingsPage().isTitleVisible();
        assertTrue("Settings title element not found on screen", titleFound);
        log.info("Verified: Settings title is visible");
    }

    @And("the following settings categories should be visible")
    public void theFollowingSettingsCategoriesShouldBeVisible(DataTable dataTable) {
        List<String> categories = dataTable.asList();
        log.info("Step → verifying {} category/ies are visible", categories.size());

        for (String category : categories) {
            settingsPage().openSection(category);
            assertTrue("Section not displayed: " + category, settingsPage().isSectionHeaderVisible(category));
            settingsPage().navigateBackToHome();
            log.info("  ✔ Found category: {}", category);
        }
    }

    // ─── When ─────────────────────────────────────────────────────────────────

    @When("the user taps on {string}")
    public void theUserTapsOn(String menuItem) {
        log.info("Step → tapping on '{}'", menuItem);
        settingsPage().openSection(menuItem);
        log.info("Tapped on '{}'", menuItem);
    }

    @Then("the user should see the {string} section")
    public void theUserShouldSeeTheSection(String sectionTitle) {
        log.info("Step → verifying section '{}' is visible", sectionTitle);
        waitelement().until(d -> settingsPage().isSectionHeaderVisible(sectionTitle));
        assertTrue("Section not displayed: " + sectionTitle, settingsPage().isSectionHeaderVisible(sectionTitle));
        log.info("Verified: section '{}' is displayed", sectionTitle);
    }

    @When("the user navigates back")
    public void theUserNavigatesBack() {
        log.info("Step → navigating back");
        settingsPage().navigateBackToHome();
        log.info("Navigated back to Settings main screen");
    }
}
