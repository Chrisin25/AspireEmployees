package com.aspire.employee.service;

import com.aspire.employee.models.Employee;
import com.aspire.employee.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmployeeService {
    @Autowired
    EmployeeRepo employeeRepo;
    public List<Employee> getEmployeeService(String startsWith){
        return employeeRepo.findAllByEmployeeNameStartingWith(startsWith);

    }


}
