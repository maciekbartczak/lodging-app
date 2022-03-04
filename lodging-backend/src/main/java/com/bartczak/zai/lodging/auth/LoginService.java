package com.bartczak.zai.lodging.auth;

import com.bartczak.zai.lodging.auth.jwt.JwtTokenUtil;
import com.bartczak.zai.lodging.user.User;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public LoginResponse login(LoginRequest loginRequest) {
        val authenticate = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(), loginRequest.getPassword()
                        )
                );

        val user = (User) authenticate.getPrincipal();
        val token = jwtTokenUtil.generateJwt(user);

        return new LoginResponse(token);
    }
}
