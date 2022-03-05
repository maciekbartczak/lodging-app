package com.bartczak.zai.lodging.foo;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foo")
@SecurityRequirement(name = "bearerAuth")
public class fooController {

    @GetMapping("/bar")
    @PreAuthorize("hasAuthority('USER')")
    public String getBar() {
        return "bar";
    }
}
