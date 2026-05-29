package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByNameContainingIgnoreCaseOrDepartment_NameContainingIgnoreCase(String nameKeyword, String departmentKeyword);

    @Query("select count(e) from Employee e")
    long countAllEmployeesForReport();

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);
}
