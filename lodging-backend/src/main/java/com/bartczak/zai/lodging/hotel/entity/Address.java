package com.bartczak.zai.lodging.hotel.entity;

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
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;
    @NotNull
    private String street;
    @NotNull
    private String city;
    @NotNull
    private String country;
    @JsonIgnore
    @OneToOne
    @MapsId
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
}
