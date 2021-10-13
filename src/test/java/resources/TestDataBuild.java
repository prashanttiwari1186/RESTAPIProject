package resources;

import pojo.AddPlace;
import pojo.Location;

import java.util.ArrayList;

public class TestDataBuild {
    /*This class is made for holding the testdata
    Below method is used for setting data for the body which is used for post call
    Sampl of the JSON which is used is shown in SampleJSON.txt file
     */

    public AddPlace addPlacePayLoad(String name, String language, String address) {


        AddPlace ap = new AddPlace();

        ap.setAccuracy(50);
        ap.setAddress(address);
        ap.setLanguage(language);
        ap.setName(name);
        ap.setPhone_number("+91-9844848290");
        ap.setWebsite("https://rahulshetteyacademy.com");

        ArrayList<String> ar = new ArrayList<>();
        ar.add("Shoe Park");
        ar.add("Shop");
        ap.setTypes(ar);

        Location loc = new Location();
        loc.setLat(-38.383494);
        loc.setLng(33.427362);
        ap.setLocation(loc);

        return ap;
    }

    public String deletePlacePayload(String place_id){

        return "{\n" +
                "\t\"place_id\" : \""+
                place_id+"\"\n" +
                "}";

    }
}
