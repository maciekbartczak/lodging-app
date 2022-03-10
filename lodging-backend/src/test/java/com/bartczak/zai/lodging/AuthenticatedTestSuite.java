package com.bartczak.zai.lodging;

import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AuthenticatedTestSuite {

    @Autowired
    protected MockLoginService mockLoginService;
    protected String jwtHeader;

    public abstract void beforeAll();

}
