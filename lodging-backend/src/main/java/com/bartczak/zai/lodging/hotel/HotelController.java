package com.bartczak.zai.lodging.hotel;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hotel")
@Tag(name = "Hotel")
public class HotelController {

    private final HotelService hotelService;

    @PostMapping("/pages")
    public HotelPageResponse getPage(@RequestBody @Valid HotelPagesRequest hotelPagesRequest) {
        return this.hotelService.getPage(hotelPagesRequest);
    }

    @PostMapping("/available")
    public AvailableHotelsResponse getAvailable(@RequestBody @Valid AvailableHotelsRequest availableHotelsRequest) {
        return this.hotelService.getAvailableHotels(availableHotelsRequest);
    }
}
