package com.bartczak.zai.lodging.hotel;

import com.bartczak.zai.lodging.IntegrationTest;
import com.bartczak.zai.lodging.TestFixture;
import com.bartczak.zai.lodging.UserTestSuite;
import com.bartczak.zai.lodging.booking.BookingDetails;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@IntegrationTest
class HotelControllerTest extends UserTestSuite {

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    void shouldGetCorrectHotelPage() {

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(HotelPagesRequest.builder()
                        .pageSize(2)
                        .pageNumber(0)
                        .build())

                .when()
                .post("/hotel/pages")

                .then()
                .statusCode(200)
                .body("totalPages", is(TestFixture.PAGES_COUNT),
                        "totalItems", is(TestFixture.HOTELS_COUNT),
                        "hotels", hasSize(2),
                        "hotels[0].id", is(1));
    }

    @Test
    void shouldGetAvailableHotels() {
        val response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(AvailableHotelsRequest.builder()
                        .bookingDetails(BookingDetails.builder()
                            .startDate(TestFixture.BOOKING_START_DATE)
                            .endDate(TestFixture.BOOKING_END_DATE)
                            .build())
                        .build())

                .when()
                .post("/hotel/available")

                .then()
                .statusCode(200)
                .body("hotels", hasSize(3))
                .extract().as(AvailableHotelsResponse.class);

        val hotelIds = response.getHotels().stream().map(Hotel::getId).collect(Collectors.toList());

        assertThat(hotelIds).containsExactlyInAnyOrderElementsOf(TestFixture.AVAILABLE_HOTEL_IDS);
    }

    @Test
    void shouldCreateHotel() {
        val response = RestAssured.given()
                .contentType(ContentType.JSON)
                .cookie(this.authCookie)
                .body(AddHotelRequest.builder()
                        .maxGuests(4)
                        .pricePerNight(BigDecimal.valueOf(200))
                        .build())

                .when()
                .post("/hotel/add")

                .then()
                .statusCode(201)
                .extract().as(HotelCreatedResponse.class);

        val created = hotelRepository.findById(response.getCreated().getId());
        assertThat(created).isPresent();
    }
}