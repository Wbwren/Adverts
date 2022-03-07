#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template




@Search
Feature: Search adverts
  I want to use this template for my feature file

  @BuyerSeachFullTerm
  Scenario: Buyer search adverts with complete term
    Given url 'http://localhost:8080/Adverts-0.0.1-SNAPSHOT/rest/adverts/search/sam'
    When method GET
    Then status 200
    
  @BuyerSeachPartialTerm
  Scenario: Buyer search adverts with partially completed term
    Given url 'http://localhost:8080/Adverts-0.0.1-SNAPSHOT/rest/adverts/search/sam'
    When method GET
    Then status 200

  @BuyerSeachPartialTerm
  Scenario Outline: Buyer search adverts with partially completed term
    Given url 'http://localhost:8080/Adverts-0.0.1-SNAPSHOT/rest/adverts/search/sam'
    When method GET
    Then status 200

    Examples: 
      | name  | value | status  |
     
