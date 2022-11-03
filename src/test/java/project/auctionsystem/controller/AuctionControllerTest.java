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
class AuctionControllerTest {

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
                .get(URL + "/auctions")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("id")
                .size();
    }

    @Test
    @Disabled("This test is disabled because it should be somehow independent")
    void getAllAuctions() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get(URL + "/auctions")
                .then()
                .assertThat()
                .statusCode(200)
                .body("size()", is(3));
    }

    @Test
    void createAuction() {
        int size = getSize();

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"username\":\"test10\",\"password\":\"password\",\"accessLevel\":{\"name\":\"CLIENT\"}}")
                .when()
                .post(URL + "/accounts")
                .then()
                .assertThat()
                .statusCode(201);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"sellerUsername\":\"test10\",\"title\":\"tytuł testowy\",\"price\":1.0,\"endDate\":\"2100-12-05T00:00:00.000\"}")
                .when()
                .post(URL + "/auctions")
                .then()
                .assertThat()
                .statusCode(201);

        assertEquals(size + 1, getSize());
    }

    @Test
    void getAuctionTest() {

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"username\":\"test4\",\"password\":\"password\",\"accessLevel\":{\"name\":\"CLIENT\"}}")
                .when()
                .post(URL + "/accounts")
                .then()
                .assertThat()
                .statusCode(201);

        UUID auctionID = RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"sellerUsername\":\"test4\",\"title\":\"tytuł testowy\",\"price\":1.0,\"endDate\":\"2100-12-05T00:00:00.000\"}")
                .when()
                .post(URL + "/auctions")
                .then()
                .assertThat()
                .statusCode(201).extract().jsonPath().getUUID("id");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .pathParam("id", auctionID)
                .get(URL + "/auctions/{id}")
                .then()
                .assertThat()
                .statusCode(200)
                .body("title", is("tytuł testowy"))
                .body("price", is(1.0F))
                .body("endDate", is("2100-12-05T00:00:00"));
    }

    @Test
    void updatePriceTest() {

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"username\":\"test5\",\"password\":\"password\",\"accessLevel\":{\"name\":\"CLIENT\"}}")
                .when()
                .post(URL + "/accounts")
                .then()
                .assertThat()
                .statusCode(201);

        UUID auctionID = RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"sellerUsername\":\"test5\",\"title\":\"tytuł testowy\",\"price\":1.0,\"endDate\":\"2100-12-05T00:00:00.000\"}")
                .when()
                .post(URL + "/auctions")
                .then()
                .assertThat()
                .statusCode(201).extract().jsonPath().getUUID("id");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .pathParam("id", auctionID)
                .param("username", "test5")
                .param("price", 200.0)
                .patch(URL + "/auctions/{id}")
                .then()
                .assertThat()
                .statusCode(200)
                .body("title", is("tytuł testowy"))
                .body("price", is(200.0F))
                .body("endDate", is("2100-12-05T00:00:00"));
    }
}
