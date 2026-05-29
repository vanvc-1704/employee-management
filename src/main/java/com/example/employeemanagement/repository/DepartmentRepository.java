package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByNameIgnoreCase(String name);

    @Query("""
            select d.name as departmentName, count(e.id) as employeeCount
            from Department d
            left join Employee e on e.department = d
            group by d.id, d.name
            order by d.name
            """)
    List<DepartmentEmployeeCountProjection> countEmployeesByDepartment();
}
