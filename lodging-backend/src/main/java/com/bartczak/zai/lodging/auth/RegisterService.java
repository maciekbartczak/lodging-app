package com.bartczak.zai.lodging.auth;

import com.bartczak.zai.lodging.user.Role;
import com.bartczak.zai.lodging.user.User;
import com.bartczak.zai.lodging.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(RegisterRequest registerRequest) {
        val user = User.builder()
                .username(registerRequest.getUsername())
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .authorities(Set.of(Role.builder().authority(Role.USER).build()))
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();
        userRepository.save(user);
    }
}
