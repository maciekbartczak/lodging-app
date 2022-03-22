package com.bartczak.zai.lodging.util;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilTest {

    @Test
    void dateShouldBeBetweenRange() {
        val date = LocalDate.of(2022, 3, 22);
        val start = LocalDate.of(2022, 3, 12);
        val end = LocalDate.of(2022, 3, 23);
        assertTrue(DateUtil.isDateBetweenRange(date, start, end));
    }

    @Test
    void dateShouldBeBetweenRangeInclusive() {
        val date = LocalDate.of(2022, 3, 22);
        val start = LocalDate.of(2022, 3, 12);
        val end = LocalDate.of(2022, 3, 22);
        assertTrue(DateUtil.isDateBetweenRange(date, start, end));
    }

    @Test
    void dateShouldNotBeBetweenRange() {
        val date = LocalDate.of(2022, 3, 22);
        val start = LocalDate.of(2022, 3, 12);
        val end = LocalDate.of(2022, 3, 20);
        assertFalse(DateUtil.isDateBetweenRange(date, start, end));
    }
}