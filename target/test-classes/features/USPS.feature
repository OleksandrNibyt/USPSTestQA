@usps
Feature: usps project

  @uspsP1
  Scenario: Validate ZIP code for Portnov Computer School
    Given I open "usps" page
    When I go to Lookup ZIP page by address
    And I fill out "4970 El Camino Real" street, "Los Altos" city, "CA" state
    Then I validate "94022" zip code exists in the result

  @uspsProject2
  Scenario: Validate ZIP code for San Bruno
    Given I open "usps" page
    When I go to Lookup ZIP page by address
    And I fill out "5301 Shelter Creek Ln" street, "San Bruno" city, "CA" state
    Then I validate "94066" zip code exists in the result

  @uspsProject3
  Scenario: Calculate price
    Given I open "usps" page
    When I go to Calculate Price Page
    And I select "United Kingdom" with "Postcard" shape
    And I define "2" quantity
    Then I calculate the price and validate cost is "$2.30"

  @uspsProject4
  Scenario: Wrong store id does not match
    Given I open "usps" page
    When I go to "Postal Store" tab
    And I enter "12345" into store search
    Then I search and validate no products found


  @uspsProject4
  Scenario: Wrong store id does not match
    Given I open "usps" page
    When I go to "Postal Store" tab
    And I enter "postcard" into store search
    Then I search and validate products found

  @usps5
  Scenario: Priority Mail
    Given I open "usps" page
    When I go to "Stamps and Supplies" section
    And I open Stamps
    And choose category Priority Mail
    Then I verify "1" item found in result


  @usps6
  Scenario: Verify color
    Given I open "usps" page
    When I go to "Stamps and Supplies" section
    And I open Stamps
    When I unselect Stamps checkbox
    And select size "Large"
    And I click "blue" color
    Then I verify "Blue" and "Large" filters
    Then I verify "US Mail T-Shirt" present in results

  @usps7
  Scenario: Verify location
    Given I open "usps" page
    When I perform "Passports" search
    And I select "Passport Application" in results
    And I click "Find a Post Office" button
    And I select "94022" zip code within "10 miles" and search
    Then I verify that "MOUNTAIN VIEW — Post Office" present in search results
    And I verify that "Street Parking Available" in "MOUNTAIN VIEW — Post Office"
    When I expand "MOUNTAIN VIEW — Post Office"
    Then I verify that "650-960-6851" phone in "MOUNTAIN VIEW — Post OfficeTM"
    When I collapse office details
    Then I verify I'm back on search results


  @usps8
  Scenario: PO Box
    Given I open "usps" page
    When I go to "PO Boxes" under "Track & Manage"
    And I reserve new PO box for "94022"
    Then I verify that "Los Altos — Post Office" present
    And I verify that "Size 5-XL" PO Box is available in "Los Altos — Post Office"


  @usps9
  Scenario: Informed delivery enabled
    Given I open "usps" page
    When I go to "Informed Delivery" section
    And I enter "94022" zip for informed delivery
    Then I verify that informed delivery is "enabled"

  @usps10
  Scenario: Informed delivery disabled
    Given I open "usps" page
    When I go to "Informed Delivery" section
    And I enter "23424" zip for informed delivery
    Then I verify that informed delivery is "disabled"

  @usps11
  Scenario: Sign-in for ship now
    Given I open "usps" page
    When I perform "Free Boxes" search
    And I set "Mail & Ship" in filters
    Then I verify that "6" results found
    When I select "Priority Mail | USPS" in results
    And I click "Ship Now" button
    Then I validate that Sign In is required

  @usps12
  Scenario: Quadcopters delivery
    Given I open "usps" page
    When I go to "Help" tab
    And I perform "Quadcopters delivery" help search
    Then I verify that no results of "Quadcopters delivery" available in help search

  @usps13
  Scenario: Every door direct mail
    Given I open "usps" page
    When I go to "Every Door Direct Mail" under "Business"
    And I search for "4970 El Camino Real, Los Altos, CA 94022"
    And I click "Show Table" on the map
    When I click "Select All" on the table
    And I close modal window
    Then I verify that summary of all rows of Cost column is equal Approximate Cost in Order Summary