package com.bartczak.zai.lodging.hotel;

import com.bartczak.zai.lodging.booking.Booking;
import com.bartczak.zai.lodging.common.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hotel extends BaseEntity {

    @NotNull
    private BigDecimal pricePerNight;
    @NotNull
    private int maxGuests;
    @NotNull
    @OneToMany(mappedBy = "hotel")
    private Set<Booking> bookings;
}
