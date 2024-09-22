package tests;

import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.util.Map;
import java.util.Set;

public class TestUpdateUser extends BaseTest {

    @Test
    public void testPutUser() {
        test = extent.createTest("Testcase to update a user");

        // Retrieve the base URL
        ConfigReader config = new ConfigReader();
        String baseurl = config.getbaseurl();
        test.log(Status.INFO, "Base URL retrieved: " + baseurl);

        // Define the endpoint for updating the user
        String putUserEndpoint = baseurl + "/users/2";
        test.log(Status.INFO, "Endpoint for user update: " + putUserEndpoint);

        // Define the body for the PUT request
        Map<String, Object> body = Map.of(
                "name", "morpheus",
                "job", "zion resident"
        );
        test.log(Status.INFO, "Request body created: " + body);

        // Send a PUT request to the API
        Response response = RestAssured.given()
                .contentType("application/json")
                .body(body)
                .put(putUserEndpoint);
        test.log(Status.INFO, "PUT request sent to the server");

        // Check if the request was successful (status code 200 or 201 for successful update)
        int statusCode = response.getStatusCode();
        try {
            Assert.assertTrue(statusCode == 200 || statusCode == 201,
                    "Expected status code 200 or 201, but got " + statusCode);
            test.log(Status.INFO, "Response status code verified: " + statusCode);
        } catch (AssertionError e) {
            logfail("Assertion failed: " + e.getMessage());
            throw e; // Re-throw to mark the test as failed
        }

        // Parse the response JSON
        Map<String, Object> putResponse = response.jsonPath().getMap("");
        test.log(Status.INFO, "Response JSON parsed successfully");

        // Expected keys in the PUT response
        Set<String> expectedPutKeys = Set.of("name", "job", "updatedAt");

        // Assert that all expected keys are present in the PUT response
        try {
            Assert.assertTrue(putResponse.keySet().containsAll(expectedPutKeys),
                    "PUT response is missing some expected keys: " + putResponse);
            test.log(Status.INFO, "All expected keys are present in the PUT response");
        } catch (AssertionError e) {
            logfail("Assertion failed: " + e.getMessage());
            throw e; // Re-throw to mark the test as failed
        }

        // Assert that the response contains the same 'name' and 'job' values as the request body
        try {
            Assert.assertEquals(putResponse.get("name"), body.get("name"),
                    "Expected name '" + body.get("name") + "', but got '" + putResponse.get("name") + "'");
            Assert.assertEquals(putResponse.get("job"), body.get("job"),
                    "Expected job '" + body.get("job") + "', but got '" + putResponse.get("job") + "'");
            test.log(Status.INFO, "Name and job verification passed");
        } catch (AssertionError e) {
            logfail("Assertion failed: " + e.getMessage());
            throw e; // Re-throw to mark the test as failed
        }
        // Log success message
        test.log(Status.INFO, "User updated successfully with response: " + putResponse);
        System.out.println("User updated successfully with response: " + putResponse);
    }
}
