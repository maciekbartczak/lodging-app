package com.bartczak.zai.lodging.foo;

import com.bartczak.zai.lodging.UserTestSuite;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

class FooControllerTest extends UserTestSuite {

    @Test
    void shouldAccessProtectedEndpoint() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .cookie(authCookie)

                .when()
                .get("/foo/bar")

                .then()
                .statusCode(200);
    }
}