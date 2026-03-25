Feature: Android Settings App

  As a QA engineer
  I want to open the Android Settings application
  So that I can verify the app launches correctly and key sections are visible

  Background:
    Given the Android Settings app is open

  @smoke1 @settings
  Scenario: Settings app launches successfully
    Then the Settings app should be displayed
    And the app title should be visible

  @smoke @settings
  Scenario: Settings main menu contains essential options
    Then the Settings app should be displayed
    And the following settings categories should be visible
      | Network & internet |
      | Connected devices  |
      | Apps               |
      | Battery            |
      | Display            |
      | Sound & vibration  |

  @smoke @settings
  Scenario: Navigate into Wi-Fi settings and return
    Then the Settings app should be displayed
    When the user taps on "Network & internet"
    Then the user should see the "Network & internet" section
    When the user navigates back
    Then the Settings app should be displayed
