package com.jsonServer.api;

import com.thedeanda.lorem.LoremIpsum;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class StructureApiTest extends BaseStructureApiTest{

    @Test
    public void getPostsListShouldSucceed() {
        given()
                .contentType("application/json")
                .baseUri(BASE_URL)
                .port(PORT)
                .log().uri()
                .when()
                .get("/posts")
                .then()
                .log().body()
                .statusCode(200);
    }

    @Test
    public void getPostDetailShouldSucceed() {
        String postId = getPostId();
        System.out.println( "postId = " + postId);

        given()
                .contentType("application/json")
                .baseUri(BASE_URL)
                .port(PORT)
                .log().uri()
                .when()
                .get("/posts/"+postId)
                .then()
                .log().body()
                .statusCode(200)
                .body("id", equalTo(postId));
    }

    @Test
    public void createPostShouldSucceed() {
        String title_name= LoremIpsum.getInstance().getTitle(3);

        Map<String, Object> sharifBody = new HashMap<>();
        sharifBody.put("title", title_name);
        sharifBody.put("views", 110);

        given()
                .contentType("application/json")
                .baseUri(BASE_URL)
                .port(PORT)
                .body(sharifBody)
                .log().uri()
                .log().body()
                .when()
                .post("/posts")
                .then()
                .log().body()
                .statusCode(201)
                .body("views", equalTo(110))
                .body("title", equalTo(title_name));
    }

    @Test
    public void putPostShouldSucceed() {
        String postId = getPostId();

        String title_name= LoremIpsum.getInstance().getTitle(3);
        Map<String, Object> sharifBody = new HashMap<>();
        sharifBody.put("title", title_name);
        sharifBody.put("views", 210);

        given()
                .contentType("application/json")
                .baseUri(BASE_URL)
                .port(PORT)
                .body(sharifBody)
                .log().uri()
                .log().body()
                .when()
                .put("/posts/"+postId)
                .then()
                .log().body()
                .statusCode(200)
                .body("views", equalTo(210))
                .body("title", equalTo(title_name));

    }

    @Test
    public void patchPostShouldSucceed() {
        String postId = getPostId();

        String title_name= LoremIpsum.getInstance().getTitle(3);
        Map<String, Object> sharifBody = new HashMap<>();
        sharifBody.put("title", title_name);


        given()
                .contentType("application/json")
                .baseUri(BASE_URL)
                .port(PORT)
                .body(sharifBody)
                .log().uri()
                .log().body()
                .when()
                .patch("/posts/"+postId)
                .then()
                .log().body()
                .statusCode(200)
                .body("title", equalTo(title_name));

    }

    @Test
    public void deletePostShouldSucceed() {
        String postId = getPostId();

        given()
                .contentType("application/json")
                .baseUri(BASE_URL)
                .port(PORT)
                .log().uri()
                .when()
                .delete("/posts/"+postId)
                .then()
                .log().body()
                .statusCode(200);
    }
}
