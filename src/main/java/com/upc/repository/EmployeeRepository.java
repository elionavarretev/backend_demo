package com.upc.repository;

import org.springframework.data.repository.CrudRepository;
import com.upc.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Integer>{

}
