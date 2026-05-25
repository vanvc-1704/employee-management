package com.example.employeemanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    /**
     * REST API endpoint - returns plain text
     * GET /hello
     */
    @GetMapping(value = "/hello", produces = "text/plain")
    public String helloApi() {
        return "Hello, Employee Management is running successfully!";
    }
}
