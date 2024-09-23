package com.aspire.employee.service;

import com.aspire.employee.models.Employee;
import com.aspire.employee.repository.AccountRepo;
import com.aspire.employee.repository.EmployeeRepo;
import com.aspire.employee.repository.StreamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmployeeService {
    @Autowired
    EmployeeRepo employeeRepo;
    @Autowired
    AccountRepo accountRepo;
    @Autowired
    StreamRepo streamRepo;
    public int addEmployee(Employee employee){
        if(employee.getEmployeeName()==null || employee.getEmployeeName().matches("")){
            throw new IllegalArgumentException("Invalid employeeName");
        }
        if(employee.getManagerId()==0 && !employee.getDesignation().matches("Manager")){
            throw new IllegalArgumentException("Only managers can have managerId 0");
        }
        if(!streamRepo.existsByStreamName(employee.getStreamName())){
            throw new IllegalArgumentException("stream not found");
        }
        if(!accountRepo.existsByAccountName(employee.getAccountName())){
            throw new IllegalArgumentException("account not found");
        }
        if(employee.getDesignation().matches("Associate")) {
            employeeRepo.save(employee);
        }
        else if(employee.getDesignation().matches("Manager")){
            if(employeeRepo.findAllEmployeesByDesignationAndStreamNameAndAccountName(employee.getDesignation(),employee.getStreamName(),employee.getAccountName()).isEmpty()){
                employeeRepo.save(employee);
            }
            else{
                throw new IllegalArgumentException("Manager already exists in the stream");
            }
        }
        return employee.getEmployeeId();
    }
    public List<Employee> getEmployeeService(String startsWith){
        return employeeRepo.findAllByEmployeeNameStartingWith(startsWith);

    }

}
