package com.appium.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SettingsPage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    private static final By SETTINGS_RECYCLER = By.id("com.android.settings:id/recycler_view");
    private static final By SETTINGS_TITLE = By.xpath("//*[@text='Settings' or @content-desc='Settings']");

    public SettingsPage(AndroidDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void waitForHome() {
        wait.until(ExpectedConditions.presenceOfElementLocated(SETTINGS_RECYCLER));
    }

    public boolean isHomeDisplayed() {
        return !driver.findElements(SETTINGS_RECYCLER).isEmpty();
    }

    public boolean isTitleVisible() {
        return !driver.findElements(SETTINGS_TITLE).isEmpty();
    }

    public void openSection(String sectionName) {
        WebElement el = findOnHomeByVisibleText(sectionName);
        wait.until(ExpectedConditions.elementToBeClickable(el)).click();
    }

    public boolean isSectionHeaderVisible(String sectionTitle) {
        By locator = By.xpath(
                "//*[@text=" + escapeXpathLiteral(sectionTitle) +
                        " or @content-desc=" + escapeXpathLiteral(sectionTitle) + "]");
        List<WebElement> els = driver.findElements(locator);
        if (!els.isEmpty()) return true;

        // Fallback: many Settings subpages show the title in a toolbar/content-desc; allow contains match.
        By contains = By.xpath("//*[contains(@text," + escapeXpathLiteral(sectionTitle) + ") or contains(@content-desc," + escapeXpathLiteral(sectionTitle) + ")]");
        return !driver.findElements(contains).isEmpty();
    }

    public void navigateBackToHome() {
        driver.navigate().back();
        waitForHome();
    }

    private WebElement findOnHomeByVisibleText(String text) {
        // First try without scrolling (fast path)
        By direct = By.xpath("//*[@text=" + escapeXpathLiteral(text) + " or @content-desc=" + escapeXpathLiteral(text) + "]");
        List<WebElement> found = driver.findElements(direct);
        if (!found.isEmpty()) return found.get(0);

        // Scroll within the recycler until the item is visible
        String uiScrollable =
                "new UiScrollable(new UiSelector().resourceId(\"com.android.settings:id/recycler_view\").scrollable(true))" +
                        ".scrollIntoView(new UiSelector().textContains(\"" + escapeForUiAutomator(text) + "\"))";
        return driver.findElement(AppiumBy.androidUIAutomator(uiScrollable));
    }

    private static String escapeForUiAutomator(String value) {
        return value.replace("\"", "\\\"");
    }

    /**
     * Returns an XPath string literal for any input (handles quotes safely).
     */
    private static String escapeXpathLiteral(String value) {
        if (!value.contains("'")) return "'" + value + "'";
        if (!value.contains("\"")) return "\"" + value + "\"";
        StringBuilder sb = new StringBuilder("concat(");
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            String part = String.valueOf(chars[i]);
            if (part.equals("'")) {
                sb.append("\"'\"");
            } else {
                sb.append("'").append(part).append("'");
            }
            if (i < chars.length - 1) sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }
}

