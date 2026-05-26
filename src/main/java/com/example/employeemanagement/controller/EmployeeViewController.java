package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.CreateEmployeeRequest;
import com.example.employeemanagement.model.Department;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.DepartmentRepository;
import com.example.employeemanagement.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeViewController {

    private final EmployeeService employeeService;
    private final DepartmentRepository departmentRepository;

    public EmployeeViewController(EmployeeService employeeService, DepartmentRepository departmentRepository) {
        this.employeeService = employeeService;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping("/list")
    public String listEmployees(Model model) {
        List<Employee> employees = employeeService.findAll(null);
        model.addAttribute("employees", employees);
        return "employees/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("request", new CreateEmployeeRequest());
        List<Department> departments = departmentRepository.findAll();
        model.addAttribute("departments", departments);
        return "employees/add";
    }

    @PostMapping("/add")
    public String addEmployee(
            @Valid CreateEmployeeRequest request,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            List<Department> departments = departmentRepository.findAll();
            model.addAttribute("departments", departments);
            return "employees/add";
        }

        employeeService.create(request);
        return "redirect:/employees/list";
    }

    @GetMapping("/search")
    public String searchEmployees(
            @RequestParam(required = false) String keyword,
            Model model
    ) {
        List<Employee> employees = employeeService.findAll(keyword);
        model.addAttribute("employees", employees);
        model.addAttribute("keyword", keyword);
        return "employees/search-results";
    }
}
