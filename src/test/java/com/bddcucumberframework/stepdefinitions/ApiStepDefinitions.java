package com.bddcucumberframework.stepdefinitions;

import com.bddcucumberframework.utils.ApiClient;
import com.bddcucumberframework.utils.ExtentReportManager;
import com.bddcucumberframework.utils.LoggerHelper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.Logger;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ApiStepDefinitions {

    private static final Logger log = LoggerHelper.getLogger();
    private Response response;

    @Given("the API base URI is {string}")
    public void the_api_base_uri_is(String baseUri) {
        log.info("Setting API base URI: {}", baseUri);
        ExtentReportManager.getTest().info("Setting API base URI: " + baseUri);
        ApiClient.setBaseUri(baseUri);
    }

    @When("the client requests {string}")
    public void the_client_requests(String path) {
        log.info("Sending GET request to {}", path);
        ExtentReportManager.getTest().info("Sending GET request to " + path);
        response = ApiClient.get(path);
    }

    @When("the client sends JSON payload from file {string} to {string}")
    public void the_client_sends_json_payload_from_file(String filePath, String apiPath) {
        log.info("Sending POST request with JSON payload from file: {}", filePath);
        ExtentReportManager.getTest().info("Sending POST request with JSON payload from file: " + filePath);
        response = ApiClient.postJsonFromFile(apiPath, filePath);
    }

    @When("the client sends XML payload from file {string} to {string}")
    public void the_client_sends_xml_payload_from_file(String filePath, String apiPath) {
        log.info("Sending POST request with XML payload from file: {}", filePath);
        ExtentReportManager.getTest().info("Sending POST request with XML payload from file: " + filePath);
        response = ApiClient.postXmlFromFile(apiPath, filePath);
    }

    @Then("the response status should be {int}")
    public void the_response_status_should_be(int expectedStatus) {
        int actualStatus = response.getStatusCode();
        log.info("Verifying status code: expected={} actual={}", expectedStatus, actualStatus);
        ExtentReportManager.getTest().info("Response status: " + actualStatus);
        assertEquals(actualStatus, expectedStatus, "Unexpected API response status.");
    }

    @And("the response body contains {string}")
    public void the_response_body_contains(String expectedText) {
        String body = response.getBody().asString();
        log.info("Verifying response body contains text.");
        ExtentReportManager.getTest().info("Response body length: " + body.length());
        assertTrue(body.contains(expectedText), "Response body did not contain expected text.");
    }

    @When("the client downloads PDF from {string} to {string}")
    public void the_client_downloads_pdf(String apiPath, String savePath) {
        log.info("Downloading PDF from {} to {}", apiPath, savePath);
        ExtentReportManager.getTest().info("Downloading PDF from " + apiPath);
        boolean downloaded = ApiClient.downloadPdf(apiPath, savePath);
        assertTrue(downloaded, "Failed to download PDF");
    }

    @Then("the PDFs should be identical comparing {string} and {string}")
    public void the_pdfs_should_be_identical(String expectedPdfPath, String actualPdfPath) {
        log.info("Comparing PDFs: {} vs {}", expectedPdfPath, actualPdfPath);
        ExtentReportManager.getTest().info("Comparing PDFs");
        boolean identical = ApiClient.comparePdfs(expectedPdfPath, actualPdfPath);
        assertTrue(identical, "PDFs are not identical");
    }
}
