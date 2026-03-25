package com.appium.utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/**
 * Manages the AndroidDriver lifecycle.
 * Uses ThreadLocal so tests can safely run in parallel if needed.
 */
public class DriverManager {

    private static final Logger log = LoggerFactory.getLogger(DriverManager.class);

    private static final ThreadLocal<AndroidDriver> driverThread = new ThreadLocal<>();

    private DriverManager() { /* utility class */ }

    // ─── Initialise Driver ────────────────────────────────────────────────────

    public static void initDriver() {
        if (driverThread.get() != null) {
            log.warn("Driver already initialised – skipping duplicate init");
            return;
        }

        UiAutomator2Options options = buildOptions();

        try {
            URL serverUrl = new URL(ConfigReader.get("appium.server.url"));
            log.info("Connecting to Appium server at {}", serverUrl);

            AndroidDriver driver = new AndroidDriver(serverUrl, options);
            driver.manage().timeouts()
                    .implicitlyWait(Duration.ofSeconds(ConfigReader.getInt("implicit.wait")));

            driverThread.set(driver);
            log.info("AndroidDriver initialised successfully");

        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Appium server URL in config.properties", e);
        }
    }

    // ─── Capabilities ─────────────────────────────────────────────────────────

    private static UiAutomator2Options buildOptions() {
        UiAutomator2Options opts = new UiAutomator2Options();

        opts.setPlatformName(ConfigReader.get("platform.name"));
        opts.setDeviceName(ConfigReader.get("device.name"));
        opts.setPlatformVersion(ConfigReader.get("platform.version"));
        opts.setAutomationName(ConfigReader.get("automation.name"));

        // Launch the Settings app directly (no APK installation needed)
        opts.setAppPackage(ConfigReader.get("app.package"));
        opts.setAppActivity(ConfigReader.get("app.activity"));

        opts.setNewCommandTimeout(Duration.ofSeconds(ConfigReader.getInt("new.command.timeout")));
        opts.setNoReset(true);   // keep app data between sessions

        log.info("Capabilities → device: {}, package: {}, activity: {}",
                ConfigReader.get("device.name"),
                ConfigReader.get("app.package"),
                ConfigReader.get("app.activity"));

        return opts;
    }

    // ─── Accessors ────────────────────────────────────────────────────────────

    public static AndroidDriver getDriver() {
        AndroidDriver driver = driverThread.get();
        if (driver == null) {
            throw new IllegalStateException("Driver not initialised. Call initDriver() first.");
        }
        return driver;
    }

    public static WebDriverWait getWait() {
        return new WebDriverWait(
                getDriver(),
                Duration.ofSeconds(ConfigReader.getInt("explicit.wait")));
    }

    // ─── Teardown ─────────────────────────────────────────────────────────────

    public static void quitDriver() {
        AndroidDriver driver = driverThread.get();
        if (driver != null) {
            log.info("Quitting AndroidDriver");
            driver.quit();
            driverThread.remove();
        }
    }
}
