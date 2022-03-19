package com.bartczak.zai.lodging.auth.session;

import lombok.experimental.UtilityClass;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

@UtilityClass
public class TokenUtil {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    public static String generateToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    public static String getTokenFromRequest(HttpServletRequest request) {
        return Arrays.stream(Optional
                        .ofNullable(request.getCookies())
                        .orElse(new Cookie[] {}))
                .filter(cookie -> cookie.getName().equals("auth-token"))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }
}
