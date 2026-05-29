package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.DepartmentStatisticsResponse;
import com.example.employeemanagement.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    private final EmployeeService employeeService;
    private final CacheManager cacheManager;

    public ReportController(EmployeeService employeeService, CacheManager cacheManager) {
        this.employeeService = employeeService;
        this.cacheManager = cacheManager;
    }

    @GetMapping("/employee-count")
    public ResponseEntity<Map<String, Long>> getEmployeeCountReport() {
        Cache cache = cacheManager.getCache("employeeCount");
        boolean cacheHit = cache != null && cache.get(SimpleKey.EMPTY) != null;
        logger.info("employee-count cache {}", cacheHit ? "HIT" : "MISS");

        long totalEmployees = employeeService.getTotalEmployeeCount();
        return ResponseEntity.ok(Map.of("totalEmployees", totalEmployees));
    }

    @GetMapping("/department-employee-count")
    public ResponseEntity<List<DepartmentStatisticsResponse>> getDepartmentEmployeeCountReport() {
        return ResponseEntity.ok(employeeService.getEmployeeCountByDepartment());
    }

    @GetMapping("/employee-statistics")
    public ResponseEntity<Map<String, Object>> getEmployeeStatistics() {
        long totalEmployees = employeeService.getTotalEmployeeCount();
        List<DepartmentStatisticsResponse> departmentStatistics = employeeService.getEmployeeCountByDepartment();

        return ResponseEntity.ok(Map.of(
                "totalEmployees", totalEmployees,
                "departmentStatistics", departmentStatistics
        ));
    }
}
