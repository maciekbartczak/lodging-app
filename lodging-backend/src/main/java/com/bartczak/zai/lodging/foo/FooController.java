package com.bartczak.zai.lodging.foo;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foo")
public class FooController {

    @GetMapping("/bar")
    @PreAuthorize("hasAuthority('USER')")
    public String getBar() {
        return "bar";
    }
}
