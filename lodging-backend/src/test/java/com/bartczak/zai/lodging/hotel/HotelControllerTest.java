package com.bartczak.zai.lodging.hotel;

import com.bartczak.zai.lodging.TestFixture;
import com.bartczak.zai.lodging.UserTestSuite;
import com.bartczak.zai.lodging.booking.BookingDetails;
import com.bartczak.zai.lodging.hotel.dto.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.math.BigDecimal;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

class HotelControllerTest extends UserTestSuite {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelImageService hotelImageService;

    @BeforeAll void setUp() {
        ReflectionTestUtils.setField(hotelImageService, "hotelImageDir", TestFixture.TEST_UPLOAD_PATH);
    }

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
                .body("totalItems", is(TestFixture.HOTELS_COUNT),
                        "hotels", hasSize(2),
                        "hotels[0].id", is(1),
                        "hotels[0].address.country", is("Poland"));
    }

    @Test
    void shouldSearchHotels() {
        val response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(HotelSearchRequest.builder()
                        .bookingDetails(BookingDetails.builder()
                            .startDate(TestFixture.BOOKING_START_DATE)
                            .endDate(TestFixture.BOOKING_END_DATE)
                            .build())
                        .city("Warsaw")
                        .build())

                .when()
                .post("/hotel/search")

                .then()
                .statusCode(200)
                .body("hotels", hasSize(2))
                .extract().as(HotelSearchResponse.class);

        val hotelIds = response.getHotels().stream().map(HotelDto::getId).collect(Collectors.toList());

        assertThat(hotelIds).containsExactlyInAnyOrderElementsOf(TestFixture.AVAILABLE_HOTEL_IDS);
    }

    @Test
    void shouldCreateHotel() {
        val hotelRequest = AddHotelRequest.builder()
                .name(TestFixture.HOTEL_NAME)
                .maxGuests(4)
                .pricePerNight(BigDecimal.valueOf(200))
                .address(AddressDto.builder()
                        .city("Warsaw")
                        .country("Poland")
                        .street("Test street")
                        .build())
                .build();
        val image = new File(TestFixture.TEST_IMAGE_PATH);

        val response = RestAssured.given()
                .cookie(this.authCookie)
                .contentType(ContentType.MULTIPART)
                .multiPart("hotel", hotelRequest, "application/json")
                .multiPart("image", image)

                .when()
                .post("/hotel/add")

                .then()
                .statusCode(201)
                .extract().as(HotelCreatedResponse.class);

        val imageName = response.getCreated().getImageName();
        val imageFile = new File(TestFixture.TEST_UPLOAD_PATH + imageName);
        assertThat(imageName).isNotBlank();
        assertThat(imageFile).exists();

        val created = hotelRepository.findById(response.getCreated().getId());
        assertThat(created).isPresent();
        assertThat(created.get().getName()).isEqualTo(TestFixture.HOTEL_NAME);
        assertThat(created.get().getCreatedBy().getId()).isEqualTo(TestFixture.TEST_USER_ID);
    }
}