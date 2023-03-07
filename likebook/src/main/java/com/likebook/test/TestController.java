package com.likebook.test;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TestController {
    @GetMapping(value = "/test")
    public Mono<String> test() {
        return Mono.just("test");
    }
}
