package com.bartczak.zai.lodging.foo;

import com.bartczak.zai.lodging.IntegrationTest;
import com.bartczak.zai.lodging.MockLoginService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

@IntegrationTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class fooControllerTest {

    @Autowired
    private MockLoginService mockLoginService;
    private String jwtHeader;

    @BeforeAll
    public void login() {
        this.jwtHeader = MockLoginService.header(mockLoginService.loginAdmin().getToken());
    }

    @Test
    public void shouldAccessProtectedEndpoint() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, jwtHeader)

                .when()
                .get("/foo/bar")

                .then()
                .statusCode(200);
    }
}