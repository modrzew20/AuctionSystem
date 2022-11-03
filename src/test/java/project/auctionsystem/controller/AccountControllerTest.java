package project.auctionsystem.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import javax.annotation.PostConstruct;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerTest {

    @LocalServerPort
    private Integer port;

    private String URL;

    @PostConstruct
    public void init() {
        URL = RestAssured.baseURI + ":" + port + "/api";
    }

    private int getSize() {
        return RestAssured.given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL + "/accounts")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("id")
                .size();
    }

    @Test
    @Disabled("This test is disabled because it should be somehow independent")
    void getAllAccounts() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get(URL + "/accounts")
                .then()
                .assertThat()
                .statusCode(200)
                .body("size()", is(3));
    }

    @Test
    void createAccount() {
        int size = getSize();

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"username\":\"username\",\"password\":\"password\",\"accessLevel\":{\"name\":\"CLIENT\"}}")
                .when()
                .post(URL + "/accounts")
                .then()
                .assertThat()
                .statusCode(201);

        assertEquals(size + 1, getSize());

    }

    @Test
    void getAccount() {

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"username\":\"test\",\"password\":\"password\",\"accessLevel\":{\"name\":\"CLIENT\"}}")
                .when()
                .post(URL + "/accounts")
                .then()
                .assertThat()
                .statusCode(201);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .pathParam("username", "test")
                .get(URL + "/accounts/{username}")
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    void getBalanceTest() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"username\":\"test2\",\"password\":\"password\",\"accessLevel\":{\"name\":\"CLIENT\"}}")
                .when()
                .post(URL + "/accounts")
                .then()
                .assertThat()
                .statusCode(201);

        double balance = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .pathParam("username", "test2")
                .pathParam("currency", "usd")
                .get(URL + "/accounts/{username}/balance/{currency}")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .jsonPath().getDouble("balance");

        assertEquals(0.0, balance);

        UUID auctionID = RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"sellerUsername\":\"test2\",\"title\":\"tytuł testowy\",\"price\":1.0,\"endDate\":\"2100-12-05T00:00:00.000\"}")
                .when()
                .post(URL + "/auctions")
                .then()
                .assertThat()
                .statusCode(201).extract().jsonPath().getUUID("id");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .pathParam("id", auctionID)
                .param("username", "test2")
                .param("price", 200.0)
                .patch(URL + "/auctions/{id}")
                .then()
                .assertThat()
                .statusCode(200)
                .body("title", is("tytuł testowy"))
                .body("price", is(200.0F))
                .body("endDate", is("2100-12-05T00:00:00"));

        balance = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .pathParam("username", "test2")
                .pathParam("currency", "usd")
                .get(URL + "/accounts/{username}/balance/{currency}")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .jsonPath().getDouble("balance");

        assertEquals(200.0, balance);

    }


}
