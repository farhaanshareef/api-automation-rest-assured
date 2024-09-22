package tests;

import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestGetUser extends BaseTest {

    @Test
    public void testGetUsers() {
        test = extent.createTest("Testcase to retrieve users");

        // Retrieve the base URL
        ConfigReader config = new ConfigReader();
        String baseurl = config.getbaseurl();
        test.log(Status.INFO, "Base URL retrieved: " + baseurl);

        // Define the endpoint for getting users
        String getUserEndpoint = baseurl + "/users?page=2";
        test.log(Status.INFO, "Endpoint for retrieving users: " + getUserEndpoint);

        // Send a GET request to the API
        Response response = RestAssured.given()
                .get(getUserEndpoint);
        test.log(Status.INFO, "GET request sent to the server");

        // Check if the request was successful (status code 200)
        int statusCode = response.getStatusCode();
        try {
            Assert.assertEquals(statusCode, 200, "Expected status code 200, but got " + statusCode);
            test.log(Status.INFO, "Response status code verified: " + statusCode);
        } catch (AssertionError e) {
            logfail("Assertion failed: " + e.getMessage());
            throw e; // Re-throw to mark the test as failed
        }

        // Parse the response JSON
        Map<String, Object> responseBody = response.jsonPath().getMap("");
        test.log(Status.INFO, "Response body parsed successfully");

        // Expected keys at the top level of the response
        Set<String> expectedKeys = Set.of("page", "per_page", "total", "total_pages", "data", "support");

        // Assert that all expected keys are present in the response
        try {
            Assert.assertTrue(responseBody.keySet().containsAll(expectedKeys),
                    "Response is missing some expected top-level keys");
            test.log(Status.INFO, "All expected top-level keys are present in the response");
        } catch (AssertionError e) {
            logfail("Assertion failed: " + e.getMessage());
            throw e;
        }

        // Expected keys for each user in the 'data' list
        Set<String> expectedUserKeys = Set.of("id", "email", "first_name", "last_name", "avatar");

        // Assert that each user in the 'data' list has the expected keys
        List<Map<String, Object>> users = (List<Map<String, Object>>) responseBody.get("data");
        for (Map<String, Object> user : users) {
            try {
                Assert.assertTrue(user.keySet().containsAll(expectedUserKeys),
                        "User data is missing some expected keys: " + user);
                test.log(Status.INFO, "User data keys verified: " + user);
            } catch (AssertionError e) {
                logfail("Assertion failed for user data: " + e.getMessage());
                throw e;
            }
        }

        // Expected keys for the 'support' object
        Set<String> expectedSupportKeys = Set.of("url", "text");

        // Assert that 'support' object has the expected keys
        Map<String, Object> support = (Map<String, Object>) responseBody.get("support");
        try {
            Assert.assertTrue(support.keySet().containsAll(expectedSupportKeys),
                    "Support object is missing some expected keys");
            test.log(Status.INFO, "Support object keys verified");
        } catch (AssertionError e) {
            logfail("Assertion failed for support object: " + e.getMessage());
            throw e;
        }

        // Log success message
        test.log(Status.INFO, "All keys are present and users retrieved successfully: " + responseBody);
        System.out.println("All keys are present and users retrieved successfully: " + responseBody);
    }
}
