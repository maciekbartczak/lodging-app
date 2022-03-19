package com.bartczak.zai.lodging.auth;

import com.bartczak.zai.lodging.auth.session.Session;
import com.bartczak.zai.lodging.auth.session.SessionRepository;
import com.bartczak.zai.lodging.auth.session.TokenUtil;
import com.bartczak.zai.lodging.user.Role;
import com.bartczak.zai.lodging.user.User;
import com.bartczak.zai.lodging.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final SessionRepository sessionRepository;
    private final AuthenticationManager authenticationManager;
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

    public Cookie login(LoginRequest loginRequest) {
        val authenticate = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(), loginRequest.getPassword()
                        )
                );


        val user = (User) authenticate.getPrincipal();

        val foo = TokenUtil.generateToken();
        val session = Session.builder()
                .userId(user.getId())
                .token(foo)
                .build();

        sessionRepository.save(session);

        return createAuthCookie(session.getToken(), false);
    }

    @Transactional
    public Cookie logout(HttpServletRequest request) {
        val token = TokenUtil.getTokenFromRequest(request);
        sessionRepository.deleteByToken(token);
        return createAuthCookie(null, true);
    }

    private Cookie createAuthCookie(String token, boolean delete) {
        val cookie = new Cookie("auth-token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        if (delete) {
            cookie.setMaxAge(0);
        }
        return cookie;
    }
}
