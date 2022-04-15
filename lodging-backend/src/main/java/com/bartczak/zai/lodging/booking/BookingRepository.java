package com.bartczak.zai.lodging.booking;

import com.bartczak.zai.lodging.booking.entity.Booking;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepository extends CrudRepository<Booking, Long> {
}
