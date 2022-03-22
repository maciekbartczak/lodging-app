package com.bartczak.zai.lodging.hotel;

import com.bartczak.zai.lodging.common.BaseEntity;
import com.bartczak.zai.lodging.util.DateUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking extends BaseEntity {

    private int guestCount;
    private LocalDate startDate;
    private LocalDate endDate;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    public boolean isDateRangeBetween(Booking other) {
        return DateUtil.isDateBetweenRange(other.startDate, this.startDate, this.endDate)
                || DateUtil.isDateBetweenRange(other.endDate, this.startDate, this.endDate);
    }
}
