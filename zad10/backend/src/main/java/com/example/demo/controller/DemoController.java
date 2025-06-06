package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @GetMapping("/api/hello")
    public String hello() {
        return "Pozdrawiam moją wspaniałą dziewczynę Olcię";
    }
}
