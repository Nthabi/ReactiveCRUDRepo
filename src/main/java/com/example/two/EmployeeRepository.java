package com.example.two;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.List;

public interface EmployeeRepository extends ReactiveCrudRepository<Employee, Long> {

        @Query("select count(*) from Employees where region={region}")
        int countByRegion(String region);

        @Query("select * from Employees where region={region}")
        List<Employee> findByRegion(String region);
}
