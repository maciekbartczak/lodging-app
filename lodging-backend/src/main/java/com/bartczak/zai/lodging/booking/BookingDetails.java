package com.bartczak.zai.lodging.booking;

import lombok.*;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetails {
    private int guestCount;
    private LocalDate startDate;
    private LocalDate endDate;
}
