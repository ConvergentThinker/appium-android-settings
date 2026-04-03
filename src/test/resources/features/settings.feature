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
  Scenario: Settings app launches successfully 2
    Then the Settings app should be displayed
    And the app title should be visible


  @smoke1 @settings
  Scenario: Settings app launches successfully 3
    Then the Settings app should be displayed
    And the app title should be visible


      @smoke1 @settings
  Scenario: Settings app launches successfully 4
    Then the Settings app should be displayed
    And the app title should be visible

  @smoke1 @settings
  Scenario: Settings app launches successfully 5
    Then the Settings app should be displayed
    And the app title should be visible

  @smoke1 @settings
  Scenario: Settings app launches successfully 6
    Then the Settings app should be displayed
    And the app title should be visible

  @smoke1 @settings
  Scenario: Settings app launches successfully 7
    Then the Settings app should be displayed
    And the app title should be visible


   @smoke1 @settings
  Scenario: Settings app launches successfully 8
    Then the Settings app should be displayed
    And the app title should be visible

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
  
