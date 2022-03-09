package com.bartczak.zai.lodging.user;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse getCurrentUser() {
        val principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        val user = userRepository.findById(principal.getId()).orElseThrow(IllegalStateException::new);

        return new UserResponse(
                UserDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .roles(user.getAuthorities().stream()
                                .map(Role::getAuthority)
                                .collect(Collectors.toList()))
                        .build()
        );
    }
}
