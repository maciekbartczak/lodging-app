package com.bartczak.zai.lodging.auth;

import com.bartczak.zai.lodging.IntegrationTest;
import com.bartczak.zai.lodging.auth.session.SessionRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@IntegrationTest
class AuthControllerTest {

    @Autowired
    private SessionRepository sessionRepository;

    @Test
    void shouldLoginWithCorrectCredentials() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(LoginRequest.builder()
                        .username("testuser")
                        .password("password")
                        .build())

                .when()
                .post("/auth/login")

                .then()
                .statusCode(200)
                .cookie("auth-token", is(notNullValue()));
    }

    @Test
    void shouldNotLoginWithInvalidCredentials() {
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
    void shouldRegisterUserWithValidRequest() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(RegisterRequest.builder()
                        .username("test@test.com")
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
    void shouldNotRegisterUserWithTooShortPassword() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(RegisterRequest.builder()
                        .username("test@test.com")
                        .firstName("test")
                        .lastName("test")
                        .password("foo")
                        .build())

                .when()
                .post("/auth/register")

                .then()
                .statusCode(400);
    }

    @Test
    void shouldCorrectlyLogout() {

        val loginResponse = performLogin();
        val token = loginResponse.cookie("auth-token");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .cookie(new Cookie
                        .Builder("auth-token", token)
                        .setSecured(true)
                        .setHttpOnly(true)
                        .build())

                .when()
                .get("/auth/logout")

                .then()
                .statusCode(200)
                .extract();

        val session = sessionRepository.findByToken(token);
        assertThat(session).isEmpty();
    }

    private ExtractableResponse<Response> performLogin() {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(LoginRequest.builder()
                        .username("foo")
                        .password("password")
                        .build())

                .when()
                .post("/auth/login")

                .then()
                .extract();
    }
}