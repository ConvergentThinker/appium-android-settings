# Appium Android Settings – Java + Maven + Cucumber

A simple mobile test automation project that opens the Android **Settings** app and verifies its main screen and navigation.

---

## 🗂 Project Structure

```
appium-android-settings/
├── pom.xml
└── src/
    └── test/
        ├── java/com/appium/
        │   ├── hooks/
        │   │   └── Hooks.java            # Before / After scenario hooks
        │   ├── runners/
        │   │   └── TestRunner.java       # JUnit 4 Cucumber runner
        │   ├── steps/
        │   │   └── SettingsSteps.java    # Step definitions
        │   └── utils/
        │       ├── ConfigReader.java     # Reads config.properties
        │       └── DriverManager.java   # AndroidDriver lifecycle
        └── resources/
            ├── config.properties        # Device & Appium config  ← EDIT THIS
            ├── logback-test.xml         # Logging config
            └── features/
                └── settings.feature    # BDD feature file
```

---

## ✅ Prerequisites

| Tool | Version | Notes |
|------|---------|-------|
| JDK | 11+ | `java -version` |
| Maven | 3.8+ | `mvn -version` |
| Appium Server | 2.x | `npm i -g appium` |
| UiAutomator2 driver | latest | `appium driver install uiautomator2` |
| Android SDK / ADB | any | emulator or real device |
| Android Emulator / Device | API 26+ | Settings app pre-installed |

---

## ⚙️ Setup

### 1 – Configure your device

Edit **`src/test/resources/config.properties`**:

```properties
appium.server.url=http://127.0.0.1:4723
device.name=emulator-5554          # ← output of: adb devices
platform.version=13.0              # ← your Android version
```

### 2 – Start Appium Server

```bash
appium
```

### 3 – Start your emulator or connect a real device

```bash
emulator -avd Pixel_6_API_33      # example emulator
# or just plug in a USB device with USB Debugging enabled
adb devices                        # verify it appears
```

---

## ▶️ Running Tests

**All smoke tests (default):**
```bash
mvn test
```

**Filter by tag:**
```bash
mvn test -Dcucumber.filter.tags="@smoke"
mvn test -Dcucumber.filter.tags="@settings"
```

**From IntelliJ IDEA:**
1. Open the project (`File → Open` → select the `appium-android-settings` folder)
2. Wait for Maven to download dependencies
3. Right-click `TestRunner.java` → **Run 'TestRunner'**

---

## 📊 Reports

After a test run, reports are generated in `target/cucumber-reports/`:

| File | Format |
|------|--------|
| `report.html` | Human-readable HTML |
| `report.json` | Machine-readable JSON |
| `report.xml` | JUnit XML (CI/CD integration) |

---

## 🔧 Common Issues

| Issue | Fix |
|-------|-----|
| `device.name` not found | Run `adb devices` and paste the exact name into `config.properties` |
| Appium connection refused | Make sure `appium` server is running on port 4723 |
| UiAutomator2 not found | Run `appium driver install uiautomator2` |
| Element not found | Adjust XPath selectors in `SettingsSteps.java` for your Android version / ROM |
| Java version error | Ensure `JAVA_HOME` points to JDK 11+ |
