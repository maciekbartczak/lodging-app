package com.bartczak.zai.lodging.booking.entity;

import com.bartczak.zai.lodging.common.BaseEntity;
import com.bartczak.zai.lodging.hotel.entity.Hotel;
import com.bartczak.zai.lodging.util.DateUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking extends BaseEntity {

    @Embedded
    @NotNull
    private BookingDetails bookingDetails;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    public boolean isDateRangeBetween(Booking other) {
        return DateUtil.isDateBetweenRange(other.bookingDetails.getStartDate(),
                this.bookingDetails.getStartDate(), this.bookingDetails.getEndDate())
                || DateUtil.isDateBetweenRange(other.bookingDetails.getEndDate(),
                this.bookingDetails.getStartDate(), this.bookingDetails.getEndDate());
    }
}
