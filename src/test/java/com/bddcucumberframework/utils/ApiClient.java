package com.bddcucumberframework.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApiClient {

    private static final Logger log = LoggerHelper.getLogger();
    private static final String TOKEN_PATTERN = "\\$@#";
    private static final int RANDOM_STRING_LENGTH = 9;
    private static final ThreadLocal<String> baseUri = new ThreadLocal<>();

    public static void setBaseUri(String uri) {
        baseUri.set(uri);
    }

    private static RequestSpecification requestSpecification() {
        RequestSpecification request = RestAssured
                .given()
                .relaxedHTTPSValidation();
        String uri = baseUri.get();
        if (uri != null && !uri.isEmpty()) {
            request.baseUri(uri);
        }
        return request;
    }

    public static Response get(String path) {
        return requestSpecification()
                .when()
                .get(path)
                .then()
                .extract()
                .response();
    }

    public static Response postJsonFromFile(String apiPath, String filePath) {
        String payload = loadAndProcessPayload(filePath);
        return post(apiPath, payload, "application/json");
    }

    public static Response postXmlFromFile(String apiPath, String filePath) {
        String payload = loadAndProcessPayload(filePath);
        return post(apiPath, payload, "application/xml");
    }

    public static Response postJson(String path, Object body) {
        return post(path, body, "application/json");
    }

    public static Response postXml(String path, String xmlPayload) {
        return post(path, xmlPayload, "application/xml");
    }

    public static Response post(String path, Object body, String contentType) {
        return requestSpecification()
                .contentType(contentType)
                .body(body)
                .when()
                .post(path)
                .then()
                .extract()
                .response();
    }

    private static String loadAndProcessPayload(String filePath) {
        try {
            String payload = new String(Files.readAllBytes(Paths.get(filePath)));
            log.info("Loaded payload from file: {}", filePath);

            if (payload.contains("$@#")) {
                String randomValue = generateRandomAlphanumeric(RANDOM_STRING_LENGTH);
                payload = payload.replaceAll(TOKEN_PATTERN, randomValue);
                log.info("Replaced token $@# with random value: {}", randomValue);

                Files.write(Paths.get(filePath), payload.getBytes());
                log.info("Updated file with new random token value: {}", filePath);
            }

            return payload;
        } catch (IOException e) {
            log.error("Error reading payload file: {}", filePath, e);
            throw new RuntimeException("Failed to load payload from file: " + filePath, e);
        }
    }

    private static String generateRandomAlphanumeric(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            result.append(characters.charAt(index));
        }
        return result.toString();
    }

    public static boolean downloadPdf(String apiPath, String savePath) {
        try {
            Path targetPath = Paths.get(savePath);
            if (apiPath.startsWith("file:") || Files.exists(Paths.get(apiPath))) {
                Path sourcePath = apiPath.startsWith("file:") ? Paths.get(new java.net.URI(apiPath)) : Paths.get(apiPath);
                if (Files.notExists(sourcePath) || !Files.isRegularFile(sourcePath)) {
                    log.error("Local PDF source path does not exist: {}", sourcePath);
                    return false;
                }
                if (targetPath.getParent() != null) {
                    Files.createDirectories(targetPath.getParent());
                }
                Files.copy(sourcePath, targetPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                log.info("PDF copied successfully from {} to {}", sourcePath, savePath);
                return true;
            }

            Response response = RestAssured
                    .given()
                    .relaxedHTTPSValidation()
                    .when()
                    .get(apiPath)
                    .then()
                    .extract()
                    .response();

            if (response.getStatusCode() == 200) {
                byte[] pdfBytes = response.getBody().asInputStream().readAllBytes();
                if (targetPath.getParent() != null) {
                    Files.createDirectories(targetPath.getParent());
                }
                Files.write(targetPath, pdfBytes);
                log.info("PDF downloaded successfully to: {}", savePath);
                return true;
            } else {
                log.error("Failed to download PDF. Status code: {}", response.getStatusCode());
                return false;
            }
        } catch (Exception e) {
            log.error("Error downloading PDF from {}: {}", apiPath, e.getMessage());
            return false;
        }
    }

    public static boolean comparePdfs(String expectedPdfPath, String actualPdfPath) {
        try {
            String expectedText = extractPdfText(expectedPdfPath);
            String actualText = extractPdfText(actualPdfPath);

            if (expectedText.equals(actualText)) {
                log.info("PDFs are identical");
                return true;
            } else {
                log.warn("PDFs are different");
                log.info("Expected length: {}, Actual length: {}", expectedText.length(), actualText.length());
                return false;
            }
        } catch (IOException e) {
            log.error("Error comparing PDFs: {}", e.getMessage());
            return false;
        }
    }

    private static String extractPdfText(String pdfPath) throws IOException {
        StringBuilder fullText = new StringBuilder();
        try (PDDocument document = PDDocument.load(new File(pdfPath))) {
            PDFTextStripper stripper = new PDFTextStripper();
            fullText.append(stripper.getText(document));
        }
        return fullText.toString();
    }
}

