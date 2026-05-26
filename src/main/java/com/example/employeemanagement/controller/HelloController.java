package com.example.employeemanagement.controller;

import com.example.employeemanagement.service.UtilityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final UtilityService utilityService;

    public HelloController(UtilityService utilityService) {
        this.utilityService = utilityService;
    }

    /**
     * GET /hello
     * Kiểm tra server chạy thành công.
     */
    @GetMapping(value = "/hello", produces = "text/plain")
    public String helloApi() {
        return "Hello, Employee Management is running successfully!";
    }

    /**
     * GET /generate-code
     * Demo UtilityService: sinh mã nhân viên tự động.
     * Ví dụ: EMP-20260526-0001
     */
    @GetMapping(value = "/generate-code", produces = "text/plain")
    public String generateCode() {
        return "Mã nhân viên: " + utilityService.generateEmployeeCode();
    }

    /**
     * GET /format-name?name=nguyen van a
     * Demo UtilityService: format tên nhân viên.
     * Ví dụ: "nguyen van a" → "Nguyen Van A"
     */
    @GetMapping(value = "/format-name", produces = "text/plain")
    public String formatName(@RequestParam String name) {
        return "Tên đã format: " + utilityService.formatEmployeeName(name);
    }

    /**
     * GET /encode-password?password=123456
     * Demo PasswordEncoder bean từ AppConfig (qua UtilityService).
     */
    @GetMapping(value = "/encode-password", produces = "text/plain")
    public String encodePassword(@RequestParam String password) {
        return "BCrypt hash: " + utilityService.encodePassword(password);
    }
}
