package com.msi.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.msi.model.Employee;

@Repository
public interface EmployeeRepository extends ReactiveMongoRepository<Employee, String> {
}
