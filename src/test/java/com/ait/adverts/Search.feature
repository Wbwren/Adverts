
@Search
Feature: Search adverts
  I want to use this template for my feature file

  @BuyerSeachFullTerm
  Scenario: Buyer search adverts with complete term
    Given url 'http://localhost:8080/Adverts/rest/adverts/search/sam'
    When method GET
    Then status 200
    
  @BuyerSeachPartialTerm
  Scenario: Buyer search adverts with partially completed term
    Given url 'http://localhost:8080/Adverts/rest/adverts/search/sam'
    When method GET
    Then status 200

  @BuyerSeachPartialTerm
  Scenario Outline: Buyer search adverts with partially completed term
    Given url 'http://localhost:8080/Adverts/rest/adverts/search/sam'
    When method GET
    Then status 200

    Examples: 
      | name  | value | status  |
     
