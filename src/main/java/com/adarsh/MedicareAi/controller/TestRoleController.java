package com.adarsh.MedicareAi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestRoleController {

    @GetMapping("/patient/test")
    public String patientApi() {
        return "Patient API accessed";
    }

    @GetMapping("/doctor/test")
    public String doctorApi() {
        return "Doctor API accessed";
    }

    @GetMapping("/admin/test")
    public String adminApi() {
        return "Admin API accessed";
    }
}