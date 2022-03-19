package com.bartczak.zai.lodging;

import io.restassured.http.Cookie;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AuthenticatedTestSuite {

    @Autowired
    protected MockLoginService mockLoginService;
    protected Cookie authCookie;

    public abstract void beforeAll();

}
