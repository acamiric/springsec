package com.learning.springsecurity.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
//controller for not auth

    @GetMapping("/hello")
    public String hello() {
        return "Hola amigo";
    }
}
