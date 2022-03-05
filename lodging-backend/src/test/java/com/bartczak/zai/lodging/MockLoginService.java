package com.bartczak.zai.lodging;

import com.bartczak.zai.lodging.auth.LoginResponse;
import com.bartczak.zai.lodging.auth.jwt.JwtTokenUtil;
import com.bartczak.zai.lodging.user.Role;
import com.bartczak.zai.lodging.user.User;
import com.bartczak.zai.lodging.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class MockLoginService {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;

    public LoginResponse loginUser() {

        val user = userRepository.findByUsername("testuser").orElse(null);
        return new LoginResponse(jwtTokenUtil.generateJwt(user));
    }

    public LoginResponse loginAdmin() {
        val user = userRepository.findByUsername("testadmin").orElse(null);
        return new LoginResponse(jwtTokenUtil.generateJwt(user));
    }

    public static String header(String token) {
        return "Bearer " + token;
    }
}
