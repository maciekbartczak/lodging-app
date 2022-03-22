package com.bartczak.zai.lodging.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@UtilityClass
public class DateUtil {

    public static boolean isDateBetweenRange(LocalDate date, LocalDate rangeStart, LocalDate rangeEnd) {
        return date.compareTo(rangeStart) >= 0 && date.compareTo(rangeEnd) <= 0;
    }
}
