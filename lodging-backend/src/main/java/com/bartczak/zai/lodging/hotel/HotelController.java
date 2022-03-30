package com.bartczak.zai.lodging.hotel;

import com.bartczak.zai.lodging.hotel.dto.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('USER')")
    public HotelCreatedResponse addHotel(@RequestPart("hotel") @Valid AddHotelRequest addHotelRequest, @RequestPart("image") MultipartFile image) {
        return this.hotelService.addHotel(addHotelRequest, image);
    }
}
