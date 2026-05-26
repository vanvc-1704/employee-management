package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByNameContainingIgnoreCaseOrDepartment_NameContainingIgnoreCase(String nameKeyword, String departmentKeyword);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);
}
