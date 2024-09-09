package com.numan.journalapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HealthCheck {

    @GetMapping
    public ResponseEntity<String> healthChecker() {
        return ResponseEntity.ok().body("ok");
    }
}
