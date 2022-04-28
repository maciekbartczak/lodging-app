package com.bartczak.zai.lodging.hotel;

import com.bartczak.zai.lodging.booking.BookingService;
import com.bartczak.zai.lodging.booking.dto.CreateBookingRequest;
import com.bartczak.zai.lodging.booking.entity.Booking;
import com.bartczak.zai.lodging.hotel.dto.*;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hotel")
@Tag(name = "Hotel")
public class HotelController {

    private final HotelService hotelService;
    private final HotelImageService hotelImageService;
    private final BookingService bookingService;

    @PostMapping("/pages")
    public HotelPageResponse getPage(@RequestBody @Valid HotelPagesRequest hotelPagesRequest) {
        return this.hotelService.getPage(hotelPagesRequest);
    }

    @PostMapping(value = "/add",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('USER')")
    public HotelCreatedResponse addHotel(@RequestPart("hotel")  @Valid
                                             @Parameter(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
                                                 AddHotelRequest addHotelRequest,
                                         @RequestPart("image")
                                             @Parameter(content = @Content(mediaType = MediaType.IMAGE_PNG_VALUE))
                                                 MultipartFile image) {
        return this.hotelService.addHotel(addHotelRequest, image);
    }

    @GetMapping("/image/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = hotelImageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .contentType(MediaType.IMAGE_PNG)
                .body(file);
    }

    @PostMapping("/{id}/booking")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Void> createBooking(@PathVariable Long id, @RequestBody CreateBookingRequest createBookingRequest) {
        bookingService.createBooking(id, createBookingRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}/booking")
    public List<Booking> getBookings(@PathVariable Long id) {
        return bookingService.getBookingsByHotelId(id);
    }

    @GetMapping("/{id}")
    public HotelDto getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id);
    }

    @GetMapping("/owned")
    @PreAuthorize("hasAuthority('USER')")
    public List<HotelDto> getOwnedHotels() {
        return hotelService.getOwnedHotels();
    }

    @GetMapping("/admin/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<HotelDto> getAll() {
        return hotelService.getAll();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        return hotelService.deleteById(id);
    }

}