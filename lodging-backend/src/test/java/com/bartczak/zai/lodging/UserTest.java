package com.bartczak.zai.lodging;

import org.junit.jupiter.api.BeforeAll;

public abstract class UserTest extends AuthenticatedTest {

    @Override
    @BeforeAll
    public void beforeAll() {
        this.jwtHeader = MockLoginService.header(mockLoginService.loginUser().getToken());
    }
}
