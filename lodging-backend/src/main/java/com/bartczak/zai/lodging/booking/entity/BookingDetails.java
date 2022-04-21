package com.bartczak.zai.lodging.booking.entity;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Embeddable
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetails {
    @NotNull
    private int guestCount;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
}
