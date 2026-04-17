Feature: Application greeting

  @team
  Scenario: Verify a welcome message
    Given the application is available
    When I request a welcome message
    Then I should receive "Hello from BDD Cucumber"
