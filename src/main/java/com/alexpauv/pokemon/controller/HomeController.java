package com.alexpauv.pokemon.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "Welcome to Pokemon!";
    }

    @GetMapping("/secure")
    public String secured() {
        return "Secret";
    }
}
