package com.example.gaming.controller;

import com.example.gaming.dto.AdminLoginRequest;
import com.example.gaming.dto.AdminLoginResponse;
import com.example.gaming.dto.DashboardResponse;
import com.example.gaming.dto.DailyIncomeResponse;
import com.example.gaming.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<AdminLoginResponse> login(@Valid @RequestBody AdminLoginRequest request) {
        AdminLoginResponse response = adminService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> getDashboard() {
        DashboardResponse response = adminService.getDashboard();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/daily-income")
    public ResponseEntity<DailyIncomeResponse> getDailyIncome(@RequestParam(required = false) LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        DailyIncomeResponse response = adminService.getDailyIncome(date);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Logout successful");
    }
}