package tests;

import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.util.Map;
import java.util.Set;

public class TestCreateUser extends BaseTest {

    @Test
    public void testCreateUser() {
        test = extent.createTest("Testcase to create a user");

        // Define the base URL of the API and endpoint
        ConfigReader config = new ConfigReader();
        String baseurl = config.getbaseurl();
        test.log(Status.INFO, "Base URL retrieved: " + baseurl);

        String postUserEndpoint = baseurl + "/users";
        test.log(Status.INFO, "Endpoint for user creation: " + postUserEndpoint);

        // Define the body for the POST request
        String expectedEmail = "eve.holt@reqres.in";
        String expectedPassword = "pistol";

        Map<String, Object> body = Map.of(
                "email", expectedEmail,
                "password", expectedPassword
        );
        test.log(Status.INFO, "Request body created: " + body);

        // Send a POST request to the API
        Response response = RestAssured.given()
                .contentType("application/json")
                .body(body)
                .post(postUserEndpoint);
        test.log(Status.INFO, "POST request sent to the server");

        // Check if the request was successful (status code 201)
        try {
            Assert.assertEquals(response.getStatusCode(), 201,
                    "Expected status code 201, but got " + response.getStatusCode());
            test.log(Status.INFO, "Response status code verified: " + response.getStatusCode());
        } catch (AssertionError e) {
            logfail("Assertion failed: " + e.getMessage());
            throw e; // Re-throw to mark the test as failed
        }

        // Parse the response JSON
        Map<String, Object> postResponse = response.jsonPath().getMap("");
        test.log(Status.INFO, "Response JSON parsed successfully");

        // Expected keys in the POST response
        Set<String> expectedPostKeys = Set.of("id", "email", "password", "createdAt");

        // Assert that all expected keys are present in the POST response
        try {
            Assert.assertTrue(postResponse.keySet().containsAll(expectedPostKeys),
                    "POST response is missing some expected keys: " + postResponse);
            test.log(Status.INFO, "All expected keys are present in the POST response");
        } catch (AssertionError e) {
            logfail("Assertion failed: " + e.getMessage());
            throw e; // Re-throw to mark the test as failed
        }

        // Assert that the email and password sent in the request body are in the response
        try {
            Assert.assertEquals(postResponse.get("email"), expectedEmail,
                    "The email in the response does not match the expected email.");
            Assert.assertEquals(postResponse.get("password"), expectedPassword,
                    "The password in the response does not match the expected password.");
            test.log(Status.INFO, "Email and password verification passed");
        } catch (AssertionError e) {
            logfail("Assertion failed: " + e.getMessage());
            throw e; // Re-throw to mark the test as failed
        }

        // Log success message
        test.log(Status.INFO, "User created successfully with response: " + postResponse);
        System.out.println("User created successfully with response: " + postResponse);
    }
}
