package com.example.employeemanagement.model;

public class Employee {

    private Long id;
    private String code;
    private String fullName;
    private String email;

    public Employee() {
    }

    public Employee(Long id, String code, String fullName, String email) {
        this.id = id;
        this.code = code;
        this.fullName = fullName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
