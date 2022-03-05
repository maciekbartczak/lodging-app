package com.bartczak.zai.lodging.auth;

import com.bartczak.zai.lodging.IntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.val;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@IntegrationTest
class AuthControllerTest {

    @Test
    public void shouldLoginWithCorrectCredentials() {
        val response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(LoginRequest.builder()
                        .username("testuser")
                        .password("password")
                        .build())

                .when()
                .post("/auth/login")

                .then()
                .statusCode(200)
                .extract().response().as(LoginResponse.class);

        assertThat(response.getToken()).isNotBlank();
    }

    @Test
    public void shouldNotLoginWithInvalidCredentials() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(LoginRequest.builder()
                        .username("testadmin")
                        .password("foo")
                        .build())

                .when()
                .post("/auth/login")

                .then()
                .statusCode(401);
    }

    @Test
    public void shouldRegisterUserWithValidRequest() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(RegisterRequest.builder()
                        .email("test@test.com")
                        .firstName("test")
                        .lastName("test")
                        .password("asdffdsa")
                        .build())

                .when()
                .post("/auth/register")

                .then()
                .statusCode(201);
    }

    @Test
    public void shouldNotRegisterUserWithInvalidEmail() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(RegisterRequest.builder()
                        .email("foo")
                        .firstName("test")
                        .lastName("test")
                        .password("asdffdsa")
                        .build())

                .when()
                .post("/auth/register")

                .then()
                .statusCode(400);
    }

    @Test
    public void shouldNotRegisterUserWithTooShortPassword() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(RegisterRequest.builder()
                        .email("test@test.com")
                        .firstName("test")
                        .lastName("test")
                        .password("foo")
                        .build())

                .when()
                .post("/auth/register")

                .then()
                .statusCode(400);
    }
}