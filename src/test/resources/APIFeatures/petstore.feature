@api
Feature: API test for petstore

Scenario Outline: Validate pet info using get call
    When user makes a get call for available pets
    Then validate the response status code <responseCode>
    Then assert the available status for each pet info received in json response
   Examples: 
    |responseCode|
    |200         | 
    
    
 Scenario Outline: Validate new pet posted to pet store using post call 
    When user post a new pet to the store 
    Then validate the response status code <responseCode>
    Then validate the id for the newly created pet in response body
   Examples: 
    |responseCode|
    |200         | 
    
    
 Scenario Outline: Validate updation of status as sold for existing available pet using put call 
    When user update status of an existing pet as "<StatusofPet>"
    Then validate the response status code <responseCode>
    Then validate status "<StatusofPet>" is changed in Json response body for the existing pet
    #Below steps are performed to revert the changes back to previous state so that testdata is found in ready state after testcase gets executed
    When user update status of an existing pet as "<revertStatusOfPet>"
    Then validate the response status code <responseCode>
    Then validate status "<revertStatusOfPet>" is changed in Json response body for the existing pet
  Examples: 
    |responseCode|StatusofPet|revertStatusOfPet|
    |200         | sold      |available        |
    
   