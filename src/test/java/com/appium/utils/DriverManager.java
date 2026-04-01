# ─────────────────────────────────────────────
#  Appium Server Configuration
# ─────────────────────────────────────────────
appium.server.url=http://127.0.0.1:4723

# ─────────────────────────────────────────────
#  Android Device / Emulator Configuration
#  Run: adb devices   →  to get your deviceName
# ─────────────────────────────────────────────
device.name=bmqc4h7lmjn7ugrg
platform.name=Android
platform.version=15
automation.name=UiAutomator2

# ─────────────────────────────────────────────
#  App Under Test  (Android Settings)
# ─────────────────────────────────────────────
app.package=com.android.settings
app.activity=com.android.settings.Settings

# ─────────────────────────────────────────────
#  Timeouts (seconds)
# ─────────────────────────────────────────────
implicit.wait=10
explicit.wait=15
new.command.timeout=60
