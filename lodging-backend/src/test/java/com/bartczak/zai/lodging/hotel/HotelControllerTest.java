package com.bartczak.zai.lodging.hotel;

import com.bartczak.zai.lodging.TestFixture;
import com.bartczak.zai.lodging.UserTestSuite;
import com.bartczak.zai.lodging.booking.dto.CreateBookingRequest;
import com.bartczak.zai.lodging.booking.entity.BookingDetails;
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
import java.time.LocalDate;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HotelControllerTest extends UserTestSuite {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelImageService hotelImageService;

    @BeforeAll
    void setUp() {
        ReflectionTestUtils.setField(hotelImageService, "hotelImageDir", TestFixture.TEST_UPLOAD_PATH);
    }

    @Test
    void shouldGetCorrectHotelPage() {

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(HotelPagesRequest.builder()
                        .filter(HotelFilter.builder()
                                .city("")
                                .bookingDetails(new BookingDetails())
                                .build())
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
    void shouldGetCorrectFilteredHotelPage() {
        val response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(HotelPagesRequest.builder()
                        .pageSize(5)
                        .pageNumber(0)
                        .filter(HotelFilter.builder()
                            .bookingDetails(BookingDetails.builder()
                                    .startDate(TestFixture.BOOKING_START_DATE)
                                    .endDate(TestFixture.BOOKING_END_DATE)
                                    .build())
                            .city("Warsaw")
                            .build())
                        .build())

                    .when()
                    .post("/hotel/pages")

                    .then()
                    .statusCode(200)
                    .body("hotels", hasSize(5))
                    .extract().as(HotelPageResponse.class);

        assertEquals(TestFixture.HOTELS_SEARCH_COUNT, response.getTotalItems());
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

    @Test
    void shouldCreateValidBooking() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .cookie(this.authCookie)
                .body(bookingRequestValid())

                .when()
                .post("/hotel/1/booking")

                .then()
                .statusCode(201);
    }

    @Test
    void shouldNotCreateBookingDateAlreadyBooked() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .cookie(this.authCookie)
                .body(bookingRequestDateAlreadyBooked())

                .when()
                .post("/hotel/3/booking")

                .then()
                .statusCode(400);
    }

    @Test
    void shouldNotCreateBookingTooMuchGuests() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .cookie(this.authCookie)
                .body(bookingRequestTooMuchGuests())

                .when()
                .post("/hotel/1/booking")

                .then()
                .statusCode(400);
    }


    private CreateBookingRequest bookingRequestValid() {
        return CreateBookingRequest.builder()
                .startDate(LocalDate.of(2022, 3, 26))
                .endDate(LocalDate.of(2022, 3, 27))
                .guestCount(2)
                .build();
    }

    private CreateBookingRequest bookingRequestDateAlreadyBooked() {
        return CreateBookingRequest.builder()
                .startDate(LocalDate.of(2022, 3, 26))
                .endDate(LocalDate.of(2022, 3, 27))
                .guestCount(2)
                .build();
    }

    private CreateBookingRequest bookingRequestTooMuchGuests() {
        return CreateBookingRequest.builder()
                .startDate(LocalDate.of(2022, 3, 26))
                .endDate(LocalDate.of(2022, 3, 27))
                .guestCount(10)
                .build();
    }
}