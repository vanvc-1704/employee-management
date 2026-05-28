package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.CreateEmployeeRequest;
import com.example.employeemanagement.dto.UpdateEmployeeRequest;
import com.example.employeemanagement.model.Department;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.DepartmentRepository;
import com.example.employeemanagement.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final UtilityService utilityService;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository,
            UtilityService utilityService
    ) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.utilityService = utilityService;
    }

    public List<Employee> findAll(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return employeeRepository.findAll();
        }

        return employeeRepository.findByNameContainingIgnoreCaseOrDepartment_NameContainingIgnoreCase(keyword, keyword);
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id=" + id));
    }

    @Transactional
    public Employee create(CreateEmployeeRequest request) {
        logger.info("Creating employee with email={} and departmentId={}", request.getEmail(), request.getDepartmentId());
        validateUniqueEmail(request.getEmail(), null);

        Department department = findDepartmentById(request.getDepartmentId());

        Employee employee = new Employee();
        employee.setCode(utilityService.generateEmployeeCode());
        employee.setName(utilityService.formatEmployeeName(request.getName()));
        employee.setEmail(request.getEmail());
        employee.setDepartment(department);

        Employee savedEmployee = employeeRepository.save(employee);
        logger.info("Created employee id={}, code={}", savedEmployee.getId(), savedEmployee.getCode());
        return savedEmployee;
    }

    @Transactional
    public Employee update(Long id, UpdateEmployeeRequest request) {
        logger.info("Updating employee id={} with new email={} and departmentId={}", id, request.getEmail(), request.getDepartmentId());
        validateUniqueEmail(request.getEmail(), id);

        Employee employee = findById(id);
        Department department = findDepartmentById(request.getDepartmentId());

        employee.setName(utilityService.formatEmployeeName(request.getName()));
        employee.setEmail(request.getEmail());
        employee.setDepartment(department);

        Employee updatedEmployee = employeeRepository.save(employee);
        logger.info("Updated employee id={}, code={}", updatedEmployee.getId(), updatedEmployee.getCode());
        return updatedEmployee;
    }

    @Transactional
    public void delete(Long id) {
        logger.info("Deleting employee id={}", id);
        Employee employee = findById(id);
        employeeRepository.delete(employee);
        logger.info("Deleted employee id={}, code={}", employee.getId(), employee.getCode());
    }

    private Department findDepartmentById(Long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with id=" + departmentId));
    }

    private void validateUniqueEmail(String email, Long id) {
        boolean existed = id == null
                ? employeeRepository.existsByEmail(email)
                : employeeRepository.existsByEmailAndIdNot(email, id);

        if (existed) {
            throw new IllegalArgumentException("Email already exists");
        }
    }
}
