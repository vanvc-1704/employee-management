package com.example.employeemanagement.dto;

public class DepartmentStatisticsResponse {

    private final String departmentName;
    private final long employeeCount;

    public DepartmentStatisticsResponse(String departmentName, long employeeCount) {
        this.departmentName = departmentName;
        this.employeeCount = employeeCount;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public long getEmployeeCount() {
        return employeeCount;
    }
}
