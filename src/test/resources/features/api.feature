Feature: API Automation
@team
  Scenario: Verify an API endpoint returns expected data
    Given the API base URI is "https://jsonplaceholder.typicode.com"
    When the client requests "/posts/1"
    Then the response status should be 200
    And the response body contains "sunt aut facere repellat provident occaecati excepturi optio reprehenderit"

  Scenario: Create entry with JSON payload from file
    Given the API base URI is "https://jsonplaceholder.typicode.com"
    When the client sends JSON payload from file "src/test/resources/payloads/sample-request.json" to "/posts"
    Then the response status should be 201

  Scenario: Create entry with XML payload from file
    Given the API base URI is "https://jsonplaceholder.typicode.com"
    When the client sends XML payload from file "src/test/resources/payloads/sample-request.xml" to "/posts"
    Then the response status should be 201

  Scenario: Download and verify PDF documents match
    Given the API base URI is "file://"
    When the client downloads PDF from "src/test/resources/documents/source-sample.pdf" to "target/downloaded-sample.pdf"
    Then the PDFs should be identical comparing "src/test/resources/documents/expected-sample.pdf" and "target/downloaded-sample.pdf"
