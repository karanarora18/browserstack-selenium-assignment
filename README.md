
# BrowserStack Selenium Automation Assignment

## Overview
This project demonstrates automated web scraping, API integration, and text processing on the El Pais website's Opinion section using Selenium, Cucumber, and TestNG. It runs tests locally and on BrowserStack in parallel on desktop and mobile browsers.

## Features
- Scrapes first 5 articles from El País Opinion section.
- Prints article titles and content in Spanish.
- Downloads article cover images (local runs only).
- Translates article headers to English using Rapid Translate API.
- Analyzes translated headers to find repeated words and counts n occurrences.
- Runs tests in parallel on BrowserStack across multiple browsers/devices.

## Tech Stack
- Java 11
- Selenium WebDriver
- Cucumber BDD
- TestNG
- BrowserStack
- Translation API (Rapid Translate)
- Maven

## Prerequisites
- Java 11 or higher
- Maven installed
- BrowserStack account and credentials set in `config.properties`
- Translation API key configured in `config.properties`

## How to Run Locally

1. Clone the repo:
   ```bash
   git clone https://github.com/karanarora18/browserstack-selenium-assignment.git
   cd <repo_folder>
   ```

2. Set environment variables or update your config:
   ```
   BROWSERSTACK_USERNAME=your_browserstack_username
   BROWSERSTACK_ACCESS_KEY=your_browserstack_access_key
   TRANSLATE_API_KEY=your_translation_api_key
   RUN_ENV=local
   or 
   RUN_ENV=browserstack
   
   ```

3. Run tests locally on Chrome:
   ```bash
   mvn clean test -Dtags="@browserstack" -DBROWSER=chrome -DRUN_ENV=local -DBROWSERSTACK_USERNAME=testUser -DBROWSERSTACK_ACCESSKEY=testKey -DRAPID_KEY=testRapidKey
   ```

## How to Run on BrowserStack (Parallel)

1. Set `RUN_ENV=remote` in config to enable BrowserStack runs.

2. Configure testng.xml in root folder for devices

3. Run tests with TestNG parallel execution:
   ```bash
   mvn clean test -Dsurefire.suiteXmlFiles=testng.xml -Dtags="@browserstack" -DRUN_ENV=browserstack -DBROWSERSTACK_USERNAME=testUser -DBROWSERSTACK_ACCESSKEY=testKey -DRAPID_KEY=testRapidKey
   ```

## Test Reports

- Cucumber HTML report: `target/cucumber-reports/cucumber.html`
- JSON report: `target/cucumber-reports/cucumber.json`
- Extent Report: `target/extent-report/`

## Screenshots

Link to screenshots of local and BrowserStack runs: 

## Notes

- Image downloading only occurs on local runs.
- Translation API has rate limits; errors are handled.
- Parallel execution configured in `testng.xml`.

---

## Author

Karan Arora   
karanarora18@gmail.com  
[LinkedIn](https://linkedin.com/in/karanarora18)
