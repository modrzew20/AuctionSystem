package project.auctionsystem.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import javax.annotation.PostConstruct;

import static org.hamcrest.core.Is.is;
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
    void getAllAccounts() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get(URL + "/accounts")
                .then()
                .assertThat()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    void getAccountById() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get(URL + "/accounts/1")
                .then()
                .assertThat()
                .statusCode(404);
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


}
