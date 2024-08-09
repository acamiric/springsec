package com.learning.springsecurity.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminOnlyController {

    //only for admins

    @GetMapping
    public String hello() {
        return "De si admine";
    }
}
