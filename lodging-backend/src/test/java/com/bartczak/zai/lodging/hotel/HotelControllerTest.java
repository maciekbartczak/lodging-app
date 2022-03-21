package com.bartczak.zai.lodging.hotel;

import com.bartczak.zai.lodging.IntegrationTest;
import com.bartczak.zai.lodging.TestFixture;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@IntegrationTest
class HotelControllerTest {

    @Test
    void shouldGetCorrectHotelPage() {

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(HotelPagesRequest.builder()
                        .pageSize(4)
                        .pageNumber(0)
                        .build())

                .when()
                .post("/hotel/pages")

                .then()
                .statusCode(200)
                .body("totalPages", is(TestFixture.PAGES_COUNT),
                        "totalItems", is(TestFixture.HOTELS_COUNT),
                        "hotels", hasSize(4),
                        "hotels[0].id", is(1));
    }
}