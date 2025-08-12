@browserstack
Feature: El Pais web scraping, API integration, and text processing

Scenario: Scrape El Pais Opinion first five articles

  Given user opens ELPais website
  When user selects "Opinion" option menu from homepage
  Then user verifies title for "Opinion" page
  Then user fetches 5 article links
  And user prints title content in Spanish and downloads article image if available
  And user translates and prints each headers in "English" language
  And user prints repeated words (>2 occurrences) in all headers combined



