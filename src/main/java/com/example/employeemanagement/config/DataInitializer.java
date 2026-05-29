package com.example.employeemanagement.config;

import com.example.employeemanagement.model.Department;
import com.example.employeemanagement.model.Role;
import com.example.employeemanagement.model.User;
import com.example.employeemanagement.repository.DepartmentRepository;
import com.example.employeemanagement.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(
            DepartmentRepository departmentRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (departmentRepository.count() == 0) {
            departmentRepository.saveAll(List.of(
                    new Department(null, "HR"),
                    new Department(null, "IT"),
                    new Department(null, "Finance")
            ));
        }

        if (userRepository.count() == 0) {
            userRepository.saveAll(List.of(
                    new User(null, "admin", passwordEncoder.encode("admin123"), Role.ADMIN),
                    new User(null, "user", passwordEncoder.encode("user123"), Role.USER)
            ));
        }
    }
}
