package tests;

import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ConfigReader;

public class TestDeleteUser extends BaseTest {

    @Test
    public void testDeleteUser() {
        test = extent.createTest("Testcase to delete a user");

        // Retrieve the base URL
        ConfigReader config = new ConfigReader();
        String baseurl = config.getbaseurl();
        test.log(Status.INFO, "Base URL retrieved: " + baseurl);

        // Define the endpoint for deleting the user
        String deleteUserEndpoint = baseurl + "/users/2";
        test.log(Status.INFO, "Endpoint for user deletion: " + deleteUserEndpoint);

        // Send a DELETE request to the API
        Response response = RestAssured.given()
                .delete(deleteUserEndpoint);
        test.log(Status.INFO, "DELETE request sent to the server");

        // Check if the request was successful (status code 204 for successful deletion)
        int statusCode = response.getStatusCode();
        try {
            Assert.assertEquals(statusCode, 204, "Expected status code 204, but got " + statusCode);
            test.log(Status.INFO, "Response status code verified: " + statusCode);
        } catch (AssertionError e) {
            logfail("Assertion failed: " + e.getMessage());
            throw e; // Re-throw to mark the test as failed
        }

        // Log success message
        test.log(Status.INFO, "User deleted successfully");
        System.out.println("User deleted successfully");
    }
}
