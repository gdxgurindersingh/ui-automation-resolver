# 🕵️‍♂️ UI Automation Resolver Assessment
## Overview
UI Automation Assessment project for Resolver! 
This project showcases a robust UI automation framework tailored for Resolver, utilizing Selenium WebDriver, TestNG, and the Page Object Model (POM) design pattern. It demonstrates best practices in automation testing to ensure web applications are tested efficiently and effectively.

## Project Structure
```dtd
/src
  /main
    /java
      /pages         # Page Objects live here
      /utils         # Utility classes and helpers

  /test
    /java
      /tests         # Test classes

/resources
  /config.properties # Configuration settings
  /testData          # Test Data
  /guide.html        # Guide File
  /index.html        # Index File

/pom.xml             # Maven Project Object Model file
/log4j2.xml          # Logging configuration
/testng.xml          # TestNG suite configuration
/README.md           # You're reading it!
```
### 🛠️ Prerequisites

Before you dive in, make sure you have the following set up on your machine:
```text
Java 8 or higher
Maven
```

### 🚀 Getting Started

Follow these steps to get your project up and running:
1.	Clone the repository:
```shell
git clone https://github.com/gdxgurindersingh/ui-automation-resolver.git
```
2.	Navigate to the project directory:
```shell
cd ui-automation-resolver-assessment
```
3.	Run the tests:
```shell
mvn clean test
```

### 📈 Generate Allure Report
Allure Reports provide a clear, visual representation of your test results. Here’s how to generate and view the Allure Report:

To generate the Allure report, run the following Maven command:
1.	Generate the report:
```shell
mvn allure:report
```
2.	Serve the report to view in your browser:
```shell
mvn allure:serve
```


### 🔄 Toggle Headless and Browser Options

For flexibility, you can toggle between headless and non-headless modes and switch browsers using Maven commands:
1.	Run tests in headless mode using Chrome:
```shell
mvn clean test -Dheadless=true -Dbrowser=chrome
```
2.	Run tests in non-headless mode using Chrome:
```shell
mvn clean test -Dheadless=false -Dbrowser=chrome
```
3.  Run tests in headless mode using Firefox:
```shell
mvn clean test -Dheadless=true -Dbrowser=firefox
```
4.  Run tests in non-headless mode using Firefox:
```shell
mvn clean test -Dheadless=false -Dbrowser=firefox
```


## 🤖 Key Features and Design Decisions

### Page Object Model (POM)

We use the POM design pattern to keep our code clean, maintainable, and reusable. Each page of the application has a corresponding Page Object class that models its behavior and elements.

### Utilities and Helpers

Our utility classes (found in the /utils directory) encapsulate common functionalities such as browser setup, logging, and configuration management. This promotes code reuse and simplifies test writing.

### Logging

We use Log4j2 for logging. Our log4j2.xml configuration file is set up to provide detailed logs, helping you understand the test flow and debug issues effectively.

### Configuration

The config.properties file holds all configurable parameters like browser type, headless mode, and URLs. This makes our tests flexible and easy to configure for different environments.



## 🧩 Fun Bits
1. Screenshot on Failure: For every failed test, a screenshot is captured and saved in the target/screenshots directory. These screenshots are also attached to the Allure Report for easy debugging. 
2. Email Reports: After a test suite runs, an email report is sent out, ensuring that stakeholders are promptly informed of the test results.


## 🛠️ Setting Up Your Environment
### For Mac:
```text
1.Install Homebrew (if not already installed):
        /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
2.Install Java: 
        brew install --cask adoptopenjdk8
3. Install Maven:
        brew install maven
```
### For Windows:
```text
1.	Install Java: Download and install the latest JDK from the official website.
2.	Install Maven: Download and install Maven from the official website.
3.	Set Up Environment Variables:
•	JAVA_HOME: Point this to your JDK installation directory.
•	MAVEN_HOME: Point this to your Maven installation directory.
•	Update PATH: Add both JAVA_HOME and MAVEN_HOME to your system PATH.
```