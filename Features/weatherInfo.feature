
Feature: Weather Information

  @AllTest
 Scenario: Validate weather information from different sources and enables a comparison between them
    Given User is on AccuWeather home page
    When User search weather information for "Mumbai" city
    And User quits the browser
    And User fetches information from api
    Then Temperature from both sources should equal