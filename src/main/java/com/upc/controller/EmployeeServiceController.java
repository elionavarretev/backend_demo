package com.upc.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.upc.exeption.EmployeeNotfoundException;
import com.upc.model.Employee;
import com.upc.service.EmployeeService;

@RestController
public class EmployeeServiceController {


    @Autowired
    private EmployeeService employeeService;

    @Operation(summary = "Get a book by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content) })
    @RequestMapping(value = "/employees", method = RequestMethod.POST)
    public ResponseEntity<Object> createEmployee(@RequestBody Employee employee)
    {
        employee = employeeService.createEmployee(employee);
        return new ResponseEntity<>("Employee is created successfully with id = " +employee.getId(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/employees/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateEmployee(@PathVariable("id") int id,
                                                 @RequestBody Employee employee)
    {
        boolean isEmployeeExist = employeeService.isEmployeeExist(id);
        if (isEmployeeExist)
        {
            employee.setId(id);
            employeeService.updateEmployee(employee);
            return new ResponseEntity<>("Employee is updated successsfully", HttpStatus.OK);
        }
        else
        {
            throw new EmployeeNotfoundException();
        }

    }

    @RequestMapping(value = "/employees/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getEmployee(@PathVariable("id") int id)
    {
        boolean isEmployeeExist = employeeService.isEmployeeExist(id);
        if (isEmployeeExist)
        {
            Employee employee = employeeService.getEmployee(id);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        }
        else
        {
            throw new EmployeeNotfoundException();
        }

    }

    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    public ResponseEntity<Object> getEmployees()
    {
        List<Employee> employeeList = employeeService.getEmployees();
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    @RequestMapping(value = "/employees/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteEmployee(@PathVariable("id") int id)
    {
        boolean isEmployeeExist = employeeService.isEmployeeExist(id);
        if (isEmployeeExist)
        {
            employeeService.deleteEmployee(id);
            return new ResponseEntity<>("Employee is deleted successsfully", HttpStatus.OK);
        }
        else
        {
            throw new EmployeeNotfoundException();
        }

    }
}
