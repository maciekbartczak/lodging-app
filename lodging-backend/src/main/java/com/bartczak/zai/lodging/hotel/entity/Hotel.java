package com.bartczak.zai.lodging.hotel.entity;

import com.bartczak.zai.lodging.booking.Booking;
import com.bartczak.zai.lodging.common.BaseEntity;
import com.bartczak.zai.lodging.user.User;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hotel extends BaseEntity {

    private String name;
    private BigDecimal pricePerNight;
    private int maxGuests;
    private String imageName;

    @OneToMany(mappedBy = "hotel")
    private Set<Booking> bookings;
    @OneToOne(mappedBy = "hotel", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User createdBy;
}
