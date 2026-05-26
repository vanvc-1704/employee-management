package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.CreateEmployeeRequest;
import com.example.employeemanagement.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EmployeeService {

    private final UtilityService utilityService;
    private final AtomicLong idSequence = new AtomicLong(1);
    private final List<Employee> employees = new CopyOnWriteArrayList<>();

    public EmployeeService(UtilityService utilityService) {
        this.utilityService = utilityService;

        // Seed data for quick API testing.
        employees.add(new Employee(idSequence.getAndIncrement(), utilityService.generateEmployeeCode(), "Nguyen Van A", "a.nguyen@example.com"));
        employees.add(new Employee(idSequence.getAndIncrement(), utilityService.generateEmployeeCode(), "Tran Thi B", "b.tran@example.com"));
    }

    public List<Employee> findAll(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return new ArrayList<>(employees);
        }

        String normalizedKeyword = keyword.toLowerCase(Locale.ROOT).trim();
        return employees.stream()
                .filter(emp -> emp.getFullName() != null && emp.getFullName().toLowerCase(Locale.ROOT).contains(normalizedKeyword))
                .toList();
    }

    public Employee create(CreateEmployeeRequest request) {
        Employee employee = new Employee();
        employee.setId(idSequence.getAndIncrement());
        employee.setCode(utilityService.generateEmployeeCode());
        employee.setFullName(utilityService.formatEmployeeName(request.getFullName()));
        employee.setEmail(request.getEmail());

        employees.add(employee);
        return employee;
    }
}
