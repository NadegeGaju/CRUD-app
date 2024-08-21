package com.example.spring_app.Entity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController


public class xx {
    @GetMapping("/api")
    public String api() {
        return "Hello World";
    }
}
