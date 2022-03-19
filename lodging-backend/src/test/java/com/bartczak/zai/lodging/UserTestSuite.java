package com.bartczak.zai.lodging;

import org.junit.jupiter.api.BeforeAll;

public abstract class UserTestSuite extends AuthenticatedTestSuite {

    @Override
    @BeforeAll
    public void beforeAll() {
        this.authCookie = mockLoginService.loginUser();
    }
}
