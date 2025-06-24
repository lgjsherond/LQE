# LQE: Selenium Java TestNG Automation Project

This project provides automated end-to-end tests for the FAO Schwarz e-commerce platform. It uses Java, Maven, TestNG, and Allure for comprehensive UI testing and reporting.

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- Getting Started
  - [Clone the repository](#clone-the-repository)
  - [Install Dependencies](#install-dependencies)
- [Running Tests](#running-tests)
- [Configuration](#configuration)
- [Reporting](#reporting)

## Features

- Product search and sorting tests
- Cart functionality validation
- Edge case handling (e.g., empty search results)
- Allure reporting integration
- Centralized logging with SLF4J
- Page Object Model for maintainable test code

## Tech Stack

- Java 11+
- Maven
- TestNG
- Allure
- SLF4J

## Project Structure

- `src/main/java/pages/` - Page Object classes
- `src/main/java/utils/` - Utility classes (config, logging)
- `src/test/java/org/sgjl/test/` - Test classes
- `src/test/java/org/sgjl/test/listeners/` - TestNG listeners (e.g., for Allure screenshots)

## Getting Started

1. **Clone the repository:**
   ```sh
   git clone https://github.com/lgjsherond/LQE.git
   cd LQE

2. **Install Dependencies**

```bash
mvn clean install
```

## Running Tests

To run all tests:
```bash
mvn test
```

To run a specific test class:
```bash
mvn -Dtest=TestClassName test
```

To run using a custom TestNG suite:
```bash
mvn test -DsuiteXmlFile=testng.xml
```

## Configuration

- Edit `testng.xml` to define test groups, parallel execution, and parameters.
- Browser and environment configuration can be set in a `global.properties` or similar file.

## Reporting
- TestNG generates reports in `target/surefire-reports` by default.
- For advanced reporting check Allure Reports.

View Allure report:
```bash
mvn allure:serve
```
