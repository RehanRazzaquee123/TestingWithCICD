# BDD Cucumber Framework

This project is a Maven-based BDD framework using Java, Cucumber, TestNG, Log4j2 and ExtentReports.

## Structure

- `src/test/resources/features` - feature files
- `src/test/java` - Cucumber runner and step definitions
- `src/main/java` - utility classes for logging and reporting
- `testng.xml` - TestNG suite configuration
- `pom.xml` - Maven build configuration

## Run tests

From the project root:

```bash
mvn clean test
```

Generated report output will be available in `test-output/extent-report.html` and the default Cucumber HTML report.
