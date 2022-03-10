package com.bartczak.zai.lodging.user;

import com.bartczak.zai.lodging.AdminTestSuite;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

class UserControllerTest extends AdminTestSuite {

    @Test
    void shouldGetCurrentUser() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, jwtHeader)

                .when()
                .get("/user/me")

                .then()
                .statusCode(200)
                .body("user.id", is(2),
                        "user.username", is("testadmin"),
                        "user.roles", containsInAnyOrder("USER", "ADMIN")
                );
    }
}