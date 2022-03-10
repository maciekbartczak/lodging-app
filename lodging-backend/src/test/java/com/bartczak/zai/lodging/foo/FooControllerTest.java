package com.bartczak.zai.lodging.foo;

import com.bartczak.zai.lodging.UserTestSuite;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

class FooControllerTest extends UserTestSuite {

    @Test
    void shouldAccessProtectedEndpoint() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, jwtHeader)

                .when()
                .get("/foo/bar")

                .then()
                .statusCode(200);
    }
}