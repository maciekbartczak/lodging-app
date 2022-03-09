package com.bartczak.zai.lodging;

import org.junit.jupiter.api.BeforeAll;

public abstract class AdminTest extends AuthenticatedTest {

    @Override
    @BeforeAll
    public void beforeAll() {
        this.jwtHeader = MockLoginService.header(mockLoginService.loginAdmin().getToken());
    }
}
