package stepDefinition;

import io.cucumber.java.Before;

import java.io.IOException;

public class Hooks {

    @Before("@DeletePlace")
    public void beforeScenario() throws IOException {
        PlaceValidation m = new PlaceValidation();
        if(PlaceValidation.place_id==null){
            m.add_place_payload_with("Prashant","Hindi","Pune");
            m.user_calls_with_http_request("addPlaceAPI","POST");
            m.verify_that_place_id_created_linked_to_the_using("Prashant","getPlaceAPI");
        }


    }
}
