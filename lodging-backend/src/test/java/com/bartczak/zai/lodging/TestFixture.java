package com.bartczak.zai.lodging;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.List;

@UtilityClass
public class TestFixture {
    public static final Long USER_SESSION_ID = 1L;
    public static final Long ADMIN_SESSION_ID = 2L;
    public static final int HOTELS_COUNT = 9;
    public static final LocalDate BOOKING_START_DATE = LocalDate.of(2022, 3, 26);
    public static final LocalDate BOOKING_END_DATE = LocalDate.of(2022, 3, 27);
    public static final List<Long> AVAILABLE_HOTEL_IDS = List.of(1L, 2L, 5L, 6L, 7L);
    public static final String HOTEL_NAME = "Test hotel";
    public static final String TEST_UPLOAD_PATH = "target/test-classes/upload/";
    public static final String TEST_IMAGE_PATH = "src/test/resources/test.png";
    public static final int HOTELS_SEARCH_COUNT = 6;
    public static final Long TEST_USER_ID = 1L;
}
