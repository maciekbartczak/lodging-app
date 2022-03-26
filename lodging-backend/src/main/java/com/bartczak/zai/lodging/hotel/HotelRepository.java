package com.bartczak.zai.lodging.hotel;

import com.bartczak.zai.lodging.hotel.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Collection<Hotel> findByBookingsIsNull();
    Collection<Hotel> findDistinctByBookingsIsNotNull();
}
