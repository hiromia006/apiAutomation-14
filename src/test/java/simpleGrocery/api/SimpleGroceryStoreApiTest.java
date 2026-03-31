package simpleGrocery.api;

import com.thedeanda.lorem.LoremIpsum;
import org.testng.annotations.Test;

import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class SimpleGroceryStoreApiTest extends BaseSimpleGroceryStoreApiTest {

    @Test(enabled = false)
    public void getStatus() {
        given()
                .spec(getHeaderSpecification())
                .log().uri()
                .when()
                .get("/status")
                .then()
                .log().body()
                .statusCode(200)
                .body("status", equalTo("UP"));
    }

    @Test(enabled = true)
    public void GetAllProductsShouldSucceed() {
        given()
                .spec(getHeaderSpecification())
                .log().uri()
                .when()
                .get("/products")
                .then()
                .log().body()
                .statusCode(200);
    }

    @Test(enabled = false)
    public void createApiClientShouldSucceed() {
        Map<String, String> client = new HashMap<>();
        client.put("clientName", LoremIpsum.getInstance().getName());
        client.put("clientEmail", LoremIpsum.getInstance().getEmail());


        given()
                .spec(getHeaderSpecification())
                .body(client)
                .log().uri()
                .log().body()
                .when()
                .post("/api-clients")
                .then()
                .log().body()
                .statusCode(201);
    }

    /*
     * Get all products
     * Create a new cart
     * Add an item to cart
     * Create a new order
     * */
    @Test(priority = 0)
    public void placeOrderShouldSucceed() {
        int productId = given()
                .spec(getHeaderSpecification())
                .log().uri()
                .when()
                .get("/products")
                .then()
//                .log().body()
                .statusCode(200)
                .extract().jsonPath().getInt("[0].id");


        String cartId = given()
                .spec(getHeaderSpecification())
                .log().uri()
                .when()
                .post("/carts")
                .then()
//                .log().body()
                .statusCode(201)
                .extract().jsonPath().getString("cartId");

        Map<String, Integer> cart = new HashMap<>();
        cart.put("productId", productId);

        given()
                .spec(getHeaderSpecification())
                .body(cart)
                .log().uri()
                .log().body()
                .when()
                .post("/carts/{cartId}/items", cartId)
                .then()
//                .log().body()
                .statusCode(201);


        Map<String, String> order = new HashMap<>();
        order.put("cartId", cartId);
        order.put("customerName", LoremIpsum.getInstance().getName());

        given()
                .spec(getHeaderSpecification())
//                .header("Authorization", "Bearer " + getBearerToken())
                .header("Authorization", "Bearer " + bearer_token)
                .body(order)
                .log().uri()
                .log().body()
                .when()
                .post("/orders")
                .then()
                .log().body()
                .statusCode(201);

    }

    @Test(priority = 1)
    public void getOrdersShouldSucceed() {
        given()
                .spec(getHeaderSpecification())
                .header("Authorization", "Bearer " + bearer_token)
                .log().uri()
                .when()
                .get("/orders")
                .then()
                .log().body()
                .statusCode(200);


    }

    @Test(priority = 2)
    public void getOrderDetailShouldSucceed() {
        String orderId = given()
                .spec(getHeaderSpecification())
                .header("Authorization", "Bearer " + bearer_token)
                .log().uri()
                .when()
                .get("/orders")
                .then()
                .extract().jsonPath().getString("[0].id");

        given()
                .spec(getHeaderSpecification())
                .header("Authorization", "Bearer " + bearer_token)
                .log().uri()
                .when()
                .get("/orders/{orderId}",orderId)
                .then()
                .log().body()
                .statusCode(200);

    }

    @Test(priority = 3)
    public void deleteOrderShouldSucceed() {
        String orderId = given()
                .spec(getHeaderSpecification())
                .header("Authorization", "Bearer " + bearer_token)
                .log().uri()
                .when()
                .get("/orders")
                .then()
                .extract().jsonPath().getString("[0].id");

        given()
                .spec(getHeaderSpecification())
                .header("Authorization", "Bearer " + bearer_token)
                .log().uri()
                .when()
                .delete("/orders/{orderId}",orderId)
                .then()
                .log().body()
                .statusCode(204);

    }
}