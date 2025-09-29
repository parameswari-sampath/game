package com.example.gaming.config;

import com.example.gaming.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AdminService adminService;

    @Override
    public void run(String... args) throws Exception {
        adminService.createDefaultAdmin();
        System.out.println("Default admin created with username: admin, password: admin123");
    }
}