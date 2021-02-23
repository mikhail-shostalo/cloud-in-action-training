package com.training.cloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @Value("${message.for.testing}")
    private String message;

    @GetMapping("/health-check")
    public ResponseEntity<String> getHealthCheck() {
        return new ResponseEntity<>(message, HttpStatus.OK);dasd
    }
}