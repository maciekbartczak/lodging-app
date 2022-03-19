package com.bartczak.zai.lodging.auth;

import com.bartczak.zai.lodging.auth.session.Session;
import com.bartczak.zai.lodging.auth.session.SessionRepository;
import com.bartczak.zai.lodging.auth.session.TokenUtil;
import com.bartczak.zai.lodging.user.User;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final SessionRepository sessionRepository;
    private final AuthenticationManager authenticationManager;

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

        return createAuthCookie(session);
    }

    private Cookie createAuthCookie(Session session) {
        val cookie = new Cookie("auth-token", session.getToken());
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }
}
