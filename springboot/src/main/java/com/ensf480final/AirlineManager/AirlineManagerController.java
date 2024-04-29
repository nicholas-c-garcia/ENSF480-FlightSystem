package com.ensf480final.AirlineManager;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")

public class AirlineManagerController {

    @GetMapping("/greet")
    public String greet() {
        return "Hello, welcome to the Spring Boot API!";
    }

    @GetMapping("/user/{name}")
    public String getUserInfo(@PathVariable String name) {
        return "Hello, " + name + "! This is your user information.";
    }
}
