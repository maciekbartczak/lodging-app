package com.bartczak.zai.lodging;

import com.bartczak.zai.lodging.auth.session.SessionRepository;
import io.restassured.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MockLoginService {

    private final SessionRepository sessionRepository;

    public Cookie loginUser() {
        val session = sessionRepository
                .findById(TestFixture.USER_SESSION_ID)
                .orElse(null);
        return new Cookie.Builder("auth-token", session.getToken())
                .setSecured(true)
                .setHttpOnly(true)
                .build();
    }

    public Cookie loginAdmin() {
        val session = sessionRepository
                .findById(TestFixture.ADMIN_SESSION_ID)
                .orElse(null);
        return new Cookie.Builder("auth-token", session.getToken())
                .setSecured(true)
                .setHttpOnly(true)
                .build();
    }

}
