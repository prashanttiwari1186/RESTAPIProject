package resources;

//This class contains all the common code which is being used in all the methods of step definition files.

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.*;
import java.util.Properties;

public class Utils {
    //This class contains the common code which is used across all the step definition files later on.
    //making it static so that it should not create multiple instance or another instance during second run
    public static RequestSpecification requestData;
    //This method as the baseURI and query or path parameter details
    public RequestSpecification requestSpecification() throws IOException {
        /* putting if condition so that log file should not get override after multiple run.
        now with below condition it will add log details of every run*/
        if (requestData==null){
            PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
            requestData = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseURL")).
                    addQueryParam("key","qaclick123").
                    addFilter(RequestLoggingFilter.logRequestTo(log)).
                    addFilter(ResponseLoggingFilter.logResponseTo(log)).
                    setContentType(ContentType.JSON).build();

            return requestData;
        }
        return requestData;
    }

    public static String getGlobalValue(String key) throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("C:\\Users\\Prashant Tiwari\\IdeaProjects\\APIAutomation\\src\\test\\java\\resources\\global.properties");
        prop.load(fis);
        return prop.getProperty(key);
    }

    public String getJsonPath(Response response, String key){
        String resp = response.asString();
        JsonPath js = new JsonPath(resp);
        return js.get(key).toString();

    }

}
