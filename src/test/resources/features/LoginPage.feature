Feature: OTC Trading Application Login
  As a user
  I want to log in to the OTC trading application
  So that I can access the trading functionality

  Background:
    Given I navigate to the login page

  @login
  Scenario: Successful login with valid credentials
    When I enter the username "<username>"
    And I enter the password "<password>"
    And I click the submit button
    Then I should be logged in successfully

  @login @invalid
  Scenario: Invalid login with invalid credentials
    When I enter the username "<username>"
    And I enter the password "<password>"
    And I click the submit button
    Then I should see an error message

  @login @invalid
  Scenario: Invalid login with empty credentials
    When I enter the username ""
    And I enter the password ""
    And I click the submit button
    Then I should see an error message