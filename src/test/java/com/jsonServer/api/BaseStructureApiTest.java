package com.jsonServer.api;

import static io.restassured.RestAssured.given;

public class BaseStructureApiTest {
    final protected String BASE_URL = "http://localhost";
    final protected int PORT = 3000;


    public String getPostId() {
        String postId = given()
                .contentType("application/json")
                .baseUri(BASE_URL)
                .port(PORT)
                .log().uri()
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .extract().jsonPath().getString("[0].id");
        return postId;
    }

}
