@smoke1
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

  @smoke1 @settings
  Scenario: Open Apps section successfully
    Then the Settings app should be displayed
    When the user taps on "Apps"
    Then the user should see the "Apps" section
    When the user navigates back
    Then the Settings app should be displayed

  @smoke1 @settings
  Scenario Outline: Open a settings section and return to home
    Then the Settings app should be displayed
    When the user taps on "<section>"
    Then the user should see the "<section>" section
    When the user navigates back
    Then the Settings app should be displayed

    Examples:
      | section  |
      | Battery  |
      | Display  |
      | About phone |
