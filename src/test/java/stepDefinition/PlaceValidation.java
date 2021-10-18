package stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class PlaceValidation extends Utils { //the class is extending util class which has the common code
   // RequestSpecification requestData;
    RequestSpecification res;
    ResponseSpecification responseData;
    Response response;
    TestDataBuild data = new TestDataBuild();
    static String place_id;
    //JSON format used is also attached in the project.(file name sample JSON)


    @Given("Add place payload with {string} {string} {string}")
    public void add_place_payload_with(String name, String language, String address) throws IOException {

        //requestSpecification() method is called from Utils class and addplacepayload has parameters passing from the featur file
        res = given().spec(requestSpecification()).body(data.addPlacePayLoad(name,language,address));
        System.out.println("This is practice line fo GIT learning");
    }

    @When("User calls {string} with {string} http request")
    public void user_calls_with_http_request(String resource, String requestMethod) {
        /*Executing when and then condition and running post request
        API resources is a Enum which has the requests resources for post/put and delete
        Constructor will be called with value of resource which you pass*/
        APIResources reourceAPI = APIResources.valueOf(resource);
        reourceAPI.getResource();
        responseData = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        //getResource method is written in enum class to get the value of resource at the run time as per the request method type
        if (requestMethod.equalsIgnoreCase("POST"))
        response = res.when().post(reourceAPI.getResource()).then().spec(responseData).extract().response();
            else if (requestMethod.equalsIgnoreCase("GET"))
            response = res.when().get(reourceAPI.getResource()).then().spec(responseData).extract().response();

    }

    @Then("The API call is success with status code {int}")
    public void the_api_call_is_success_with_status_code(Integer int1) {

        int res = response.getStatusCode();
        assertEquals(res,200);
    }

    @Then("{string} in response body is {string}")
    public void in_response_body_is(String actValues, String expValues) {

         assertEquals(getJsonPath(response,actValues).toString(),(expValues));

    }

    @Given("Verify that place_id created linked to the {string} using {string}")
    public void verify_that_place_id_created_linked_to_the_using(String expectedName, String resouce) throws IOException {

        place_id = getJsonPath(response,"place_id").toString();
        System.out.println(place_id);
        res = given().spec(requestSpecification()).queryParam("place_id", place_id);
        user_calls_with_http_request(resouce, "GET");
        String actualName = getJsonPath(response,"name");
        assertEquals(actualName,expectedName);


    }

    @Given("deletePlace_payload")
    public void delete_place_payload() throws IOException {

        res=given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));

    }
}
