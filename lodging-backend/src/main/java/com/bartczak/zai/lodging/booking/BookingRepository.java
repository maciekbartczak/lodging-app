package com.bartczak.zai.lodging.booking;

import com.bartczak.zai.lodging.booking.entity.Booking;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookingRepository extends CrudRepository<Booking, Long> {
    List<Booking> getAllByHotel_Id(Long id);
}
