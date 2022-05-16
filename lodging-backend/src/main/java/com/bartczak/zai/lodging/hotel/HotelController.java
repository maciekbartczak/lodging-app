package com.bartczak.zai.lodging.hotel;

import com.bartczak.zai.lodging.hotel.dto.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hotel")
@Tag(name = "Hotel")
public class HotelController {

    private final HotelService hotelService;
    private final HotelImageService hotelImageService;

    @PostMapping("/pages")
    public HotelPageResponse getPage(@RequestBody @Valid HotelPagesRequest hotelPagesRequest) {
        return this.hotelService.getPage(hotelPagesRequest);
    }

    @PostMapping(value = "/add")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('USER')")
    public HotelCreatedResponse addHotel(@RequestBody @Valid AddHotelRequest addHotelRequest) {
        return this.hotelService.addHotel(addHotelRequest);
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

    @GetMapping("/{id}")
    public HotelDto getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id);
    }

    @GetMapping("/owned")
    @PreAuthorize("hasAuthority('USER')")
    public List<HotelDto> getOwnedHotels() {
        return hotelService.getOwnedHotels();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        return hotelService.deleteById(id);
    }

    @PostMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Void> editHotel(@PathVariable Long id, @RequestBody AddHotelRequest editHotelRequest) {
        return hotelService.editHotel(id, editHotelRequest);
    }

    @GetMapping("/admin/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<HotelDto> getAll() {
        return hotelService.getAll();
    }

}