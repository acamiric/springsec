package com.learning.springsecurity.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloAuthenticatedController {
//controller for authenticated user
    @GetMapping("/helloAuth")
    private String hello() {
        return "Hola amigo authenticato";
    }
}
