package project.auctionsystem.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import javax.annotation.PostConstruct;

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
                .body("{\"name\":\"name\",\"description\":\"description\",\"startingPrice\":1.0,\"endDate\":\"2022-05-05T00:00:00.000+00:00\",\"seller\":{\"username\":\"username\",\"password\":\"password\",\"accessLevel\":{\"name\":\"CLIENT\"}}}")
                .when()
                .post(URL + "/auctions")
                .then()
                .assertThat()
                .statusCode(200);

        assertEquals(size + 1, getSize());
    }
}
