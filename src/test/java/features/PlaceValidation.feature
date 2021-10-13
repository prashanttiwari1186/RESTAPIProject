Feature: Validating place APIs
  @AddPlace
  Scenario Outline: Verify if place is added successfully using add API.
    Given Add place payload with "<name>" "<language>" "<address>"
    When User calls "addPlaceAPI" with "POST" http request
    Then The API call is success with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    And Verify that place_id created linked to the "<name>" using "getPlaceAPI"
    Examples:
      |name  |language |address |
      |PTHouse|French|Lyon Street|
     # |ShlokHouse|German|Berlin Street|

  @DeletePlace
  Scenario: Verify if place is deleted succesfully using delete API
    Given deletePlace_payload
    When User calls "deletePlaceAPI" with "POST" http request
    Then The API call is success with status code 200
    And "status" in response body is "OK"



