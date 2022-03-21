package com.bartczak.zai.lodging.hotel;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelPageResponse getPage(HotelPagesRequest hotelPagesRequest) {
        val pageRequest = PageRequest.of(hotelPagesRequest.getPageNumber(), hotelPagesRequest.getPageSize());

        val hotelPage = hotelRepository.findAll(pageRequest);
        return HotelPageResponse.builder()
                .totalPages(hotelPage.getTotalPages())
                .totalItems(hotelPage.getTotalElements())
                .hotels(hotelPage.toList())
                .build();
    }
}
