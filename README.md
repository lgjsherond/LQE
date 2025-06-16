# LQE: Selenium Java TestNG Automation Project

This repository contains a sample Selenium TestNG project for practice purpose

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Project Structure](#project-structure)
- [Setup](#setup)
- [Running Tests](#running-tests)
- [Configuration](#configuration)
- [Reporting](#reporting)
- [Contributing](#contributing)
- [License](#license)

## Overview

LQE is an example test automation suite. It is designed for maintainability, scalability, and ease of use in automating the testing of web applications.

## Features

- Selenium WebDriver-based browser automation
- Test orchestration and assertions with TestNG
- Page Object Model (POM) for maintainable code
- Cross-browser support (Chrome, Firefox, etc.)
- Easy configuration and extensibility
- Rich reporting and logging

## Project Structure

```
project-root/
│
├── src/
│   ├── main/java/        # Framework and support code
│   └── test/java/        # Test cases, page objects, utilities
│
├── testng.xml            # TestNG suite configuration
├── pom.xml               # Maven project and dependency management
└── README.md             # Project documentation
```

## Setup

### Prerequisites

- Java JDK 1 or above
- Maven 3.x
- Git

### Install Dependencies

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
- Browser and environment configuration can be set in a `config.properties` or similar file.

## Reporting

- TestNG generates reports in `target/surefire-reports` by default.
- For advanced reporting, integrate with tools like Allure or Extent Reports.
