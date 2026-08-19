package api;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class ApiClient {

    // Method to send a POST request to create data (like a new user)
    public static Response sendPostRequest(String endpoint, String jsonBody) {
        Response response = given()
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();

        return response;
    }
}