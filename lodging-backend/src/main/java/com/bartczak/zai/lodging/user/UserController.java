package com.bartczak.zai.lodging.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name = "User")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('USER')")
    public UserResponse getCurrentUser() {
        return userService.getCurrentUser();
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/{id}/promote")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> promoteUser(@PathVariable Long id) {
        return userService.promoteUser(id);
    }

    @PostMapping("/{id}/demote")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> demoteUser(@PathVariable Long id) {
        return userService.demoteUser(id);
    }
}
