Feature: UI Verification

  @team
  Scenario: Launch Myntra and verify title and text
    Given the user opens Myntra
    Then the page title should contain "Myntra"
    And the page should display text "Online shopping"
    And the browser is closed

  @team
  Scenario: Launch Ministry of Testing and verify title and text
    Given the user opens Ministry of Testing
    Then the page title should contain "Ministry of Testing"
    And the page should display text "Welcome"
    And the browser is closed
