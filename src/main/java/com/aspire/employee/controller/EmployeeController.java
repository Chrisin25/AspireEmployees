package com.aspire.employee.controller;

import com.aspire.employee.models.Employee;
import com.aspire.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController("/api/v1")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;
    @GetMapping("/employees")
    public List<Employee> getCategory(
            @RequestParam(required = false) String startsWith){
        return employeeService.getEmployeeService(startsWith);
    }
}
