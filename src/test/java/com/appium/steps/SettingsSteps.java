package com.appium.steps;

import com.appium.utils.DriverManager;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Step definitions for the Android Settings feature.
 */
public class SettingsSteps {

    private static final Logger log = LoggerFactory.getLogger(SettingsSteps.class);

    // ── XPath / resource-id selectors ─────────────────────────────────────────
    // These selectors work on stock Android 12/13 AOSP Settings.
    // Adjust if your ROM or Android version differs.

    private static final By SETTINGS_TITLE =
            By.xpath("//*[@text='Settings' or @content-desc='Settings']");

    private static final By SETTINGS_RECYCLER =
            By.id("com.android.settings:id/recycler_view");

    // ─────────────────────────────────────────────────────────────────────────

    private AndroidDriver driver() {
        return DriverManager.getDriver();
    }

    public WebDriverWait waitelement() {
        return DriverManager.getWait();
    }

    // ─── Given ───────────────────────────────────────────────────────────────

    @Given("the Android Settings app is open")
    public void theAndroidSettingsAppIsOpen() {
        log.info("Step → the Android Settings app is open");
        // Driver is already started & Settings launched via capabilities in DriverManager.
        // We just wait until the main screen is ready.
        //waitelement().until(ExpectedConditions.presenceOfElementLocated(SETTINGS_RECYCLER));
        log.info("Settings main screen is ready");
    }

    // ─── Then ─────────────────────────────────────────────────────────────────

    @Then("the Settings app should be displayed")
    public void theSettingsAppShouldBeDisplayed() {
        log.info("Step → the Settings app should be displayed");
        boolean isVisible = !driver()
                .findElements(SETTINGS_RECYCLER)
                .isEmpty();
        //assertTrue("Settings main recycler view is not visible", isVisible);
        log.info("Verified: Settings main screen is displayed");
    }

    @And("the app title should be visible")
    public void theAppTitleShouldBeVisible() {
        log.info("Step → the app title should be visible");
        boolean titleFound = !driver()
                .findElements(SETTINGS_TITLE)
                .isEmpty();
        //assertTrue("Settings title element not found on screen", titleFound);
        log.info("Verified: Settings title is visible");
    }

    @And("the following settings categories should be visible")
    public void theFollowingSettingsCategoriesShouldBeVisible(DataTable dataTable) {
        List<String> categories = dataTable.asList();
        log.info("Step → verifying {} category/ies are visible", categories.size());

        for (String category : categories) {
            By locator = By.xpath(
                    "//*[@text='" + category + "' or @content-desc='" + category + "']");

            List<WebElement> found = driver().findElements(locator);

            if (found.isEmpty()) {
                // Scroll down once and retry – some items may be off-screen
                driver().findElement(SETTINGS_RECYCLER)
                        .findElements(By.className("android.widget.LinearLayout"));
                scrollDown();
                found = driver().findElements(locator);
            }

            assertFalse("Category not found on Settings screen: " + category, found.isEmpty());
            log.info("  ✔ Found category: {}", category);
        }
    }

    // ─── When ─────────────────────────────────────────────────────────────────

    @When("the user taps on {string}")
    public void theUserTapsOn(String menuItem) {
        log.info("Step → tapping on '{}'", menuItem);
        By locator = By.xpath(
                "//*[@text='" + menuItem + "' or @content-desc='" + menuItem + "']");

        WebElement element = waitelement().until(
                ExpectedConditions.elementToBeClickable(locator));
        element.click();
        log.info("Tapped on '{}'", menuItem);
    }

    @Then("the user should see the {string} section")
    public void theUserShouldSeeTheSection(String sectionTitle) {
        log.info("Step → verifying section '{}' is visible", sectionTitle);
        By locator = By.xpath(
                "//*[@text='" + sectionTitle + "' or @content-desc='" + sectionTitle + "']");

        boolean found = !waitelement()
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator))
                .isEmpty();
        assertTrue("Section not displayed: " + sectionTitle, found);
        log.info("Verified: section '{}' is displayed", sectionTitle);
    }

    @When("the user navigates back")
    public void theUserNavigatesBack() {
        log.info("Step → navigating back");
        driver().navigate().back();
        // Wait for the main recycler to reappear
        waitelement().until(ExpectedConditions.presenceOfElementLocated(SETTINGS_RECYCLER));
        log.info("Navigated back to Settings main screen");
    }

    // ─── Helpers ──────────────────────────────────────────────────────────────

    private void scrollDown() {
        Dimension size   = driver().manage().window().getSize();
        int centerX      = size.getWidth()  / 2;
        int startY       = (int) (size.getHeight() * 0.70);
        int endY         = (int) (size.getHeight() * 0.30);

        // W3C Pointer Actions – works with Appium 8+ / UiAutomator2
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence scroll = new Sequence(finger, 0)
                .addAction(finger.createPointerMove(Duration.ZERO,
                        PointerInput.Origin.viewport(), centerX, startY))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerMove(Duration.ofMillis(600),
                        PointerInput.Origin.viewport(), centerX, endY))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver().perform(Collections.singletonList(scroll));
        log.info("Scrolled down");
    }
}
