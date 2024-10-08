package com.aspire.employee.controller;

import com.aspire.employee.models.Employee;
import com.aspire.employee.models.Stream;
import com.aspire.employee.response.ResponseMessage;
import com.aspire.employee.response.ResponseMessageForCreate;
import com.aspire.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;
    @PostMapping("/employee")
    public ResponseEntity<ResponseMessageForCreate> addEmployee(@RequestBody Employee employee)
    {
        int id;
        id = employeeService.addEmployee(employee);
        return new ResponseEntity<>(new ResponseMessageForCreate("Successfully created",id), HttpStatus.CREATED);
    }
    @GetMapping("/employee")
    public List<Employee> getEmployee(
            @RequestParam String startsWith){
        return employeeService.getEmployeeService(startsWith);
    }
    @GetMapping("/streams")
    public List<Stream> getStreams()
    {
        return employeeService.getAllStreams();
    }

    //PUT /api/v1/employees?employee-id={employee_id}&manager-id={manager_id}&account-name={account_name}&designation={designation}

    //update employee details
    @PutMapping("/employee")
    public ResponseEntity<ResponseMessage> updateEmployee(
            @RequestParam Integer employeeId,
            @RequestParam(required = false) Integer managerId,
            @RequestParam(required = false) String designation
    )
    {
        ResponseMessage responseMessage = new ResponseMessage();
        if(managerId==null && designation==null){
        responseMessage.setMessage("No details for update");
        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }
        employeeService.updateEmployee(employeeId,managerId,designation);
        responseMessage.setMessage("Successfully updated employee details");
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }
}
