package com.bartczak.zai.lodging.user;

import com.bartczak.zai.lodging.auth.RoleRepository;
import com.bartczak.zai.lodging.common.InvalidRequestException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

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

    public ResponseEntity<Void> promoteUser(Long id) {
        val user = userRepository
                .findById(id)
                .orElseThrow(() -> new InvalidRequestException("User with id " + id + " does not exist"));
        val adminRole = roleRepository.findByAuthority(Role.ADMIN)
                .orElse(Role.builder().authority(Role.ADMIN).build());
        user.getAuthorities().add(adminRole);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(u -> UserDto.builder()
                        .id(u.getId())
                        .username(u.getUsername())
                        .firstName(u.getFirstName())
                        .lastName(u.getLastName())
                        .roles(u.getAuthorities().stream().map(Role::getAuthority).collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

    public ResponseEntity<Void> demoteUser(Long id) {
        val user = userRepository
                .findById(id)
                .orElseThrow(() -> new InvalidRequestException("User with id " + id + " does not exist"));
        user.getAuthorities().removeIf(r -> r.getAuthority().equals(Role.ADMIN));
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }
}
