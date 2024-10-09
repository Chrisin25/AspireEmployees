package com.aspire.employee.service;

import com.aspire.employee.models.Employee;
import com.aspire.employee.models.Stream;
import com.aspire.employee.repository.AccountRepo;
import com.aspire.employee.repository.EmployeeRepo;
import com.aspire.employee.repository.StreamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepo employeeRepo;
    @Autowired
    AccountRepo accountRepo;
    @Autowired
    StreamRepo streamRepo;
    public int addEmployee(Employee employee) {
        if (employee.getEmployeeId() != null) {
            throw new IllegalArgumentException("ID should be auto-generated");
        }
        if (employee.getEmployeeName() == null || employee.getEmployeeName().isEmpty()) {
            throw new IllegalArgumentException("Specify a valid employee name");
        }
        if (employee.getDesignation() == null || employee.getStreamName() == null ||
                employee.getAccountName() == null || employee.getManagerId() == null) {
            throw new IllegalArgumentException("Designation, stream name, account name, and managerId should have values");
        }
        if (employee.getManagerId() == 0 && !employee.getDesignation().equals("Manager")) {
            throw new IllegalArgumentException("Only managers can have managerId 0");
        } else if (employee.getDesignation().equals("Manager") && employee.getManagerId() != 0) {
            throw new IllegalArgumentException("Manager must have managerId 0");
        }
        Stream stream = streamRepo.findByStreamName(employee.getStreamName());
        if (stream == null || !stream.getAccountName().equals(employee.getAccountName())) {
            throw new IllegalArgumentException("Stream or account not found, or mismatch");
        }    if (employee.getDesignation().equals("Associate")) {
            if (!employeeRepo.existsByEmployeeIdAndDesignationAndStreamName(
                    employee.getManagerId(), "Manager", employee.getStreamName())) {
                throw new IllegalArgumentException("Manager with the specified ID does not exist in the stream");
            }
            employeeRepo.save(employee);    } else if (employee.getDesignation().equals("Manager")) {
            if (employeeRepo.findAllEmployeesByDesignationAndStreamNameAndAccountName(
                    employee.getDesignation(), employee.getStreamName(), employee.getAccountName()).isEmpty()) {
                employeeRepo.save(employee);
            } else {
                throw new IllegalArgumentException("Manager already exists in the stream");
            }    } else {        throw new IllegalArgumentException("Invalid designation");
        }
        return employee.getEmployeeId();
    }
    public List<Employee> getEmployeeService(String startsWith){
        List<Employee> employeeList;
        if(startsWith.isEmpty()){
            throw new IllegalArgumentException("enter valid alphabet");
        }
        employeeList=employeeRepo.findAllByEmployeeNameStartingWith(startsWith);
        if(employeeList.isEmpty()){
            throw new IllegalArgumentException("no employee name starts with "+startsWith);
        }
        return employeeList;

    }
    public List<Stream> getAllStreams() {
        List<Stream> streamList=streamRepo.findAll();
        if(streamList.isEmpty()){
            throw new IllegalArgumentException("no streams present");
        }
        return streamList;
    }

    public void updateEmployee(Integer employeeId, Integer managerId, String designation) {
        Optional<Employee> validEmployee = employeeRepo.findById(employeeId);
        if(validEmployee.isPresent())
        {
            Employee employee = validEmployee.get();
            if(designation!=null)
            {
                if(designation.equalsIgnoreCase("manager"))
                {
                    String streamName = employee.getStreamName();

                    Optional<Employee> validManager = employeeRepo.findByStreamAndManagerIdEquals(streamName, 0);

                    if(validManager.isPresent())
                    {
                        throw new IllegalArgumentException("A valid Manager is present for the current stream ");
                    }
                    else {
                        employee.setManagerId(0);
                    }
                }
                else if(designation.equalsIgnoreCase("associate"))
                {
                    if(employee.getManagerId()!=0)
                    {
                        throw new IllegalArgumentException("Already an associate!.");
                    }
                    else {
                        List<Employee> employees = employeeRepo.findAllByManagerId(employee.getEmployeeId());
                        if(employees.isEmpty())
                        {
                            Optional<Employee> validManager = employeeRepo.findByIdAndManagerIdEqualsZero(managerId);
                            if (validManager.isPresent()) {
                                Employee manager = validManager.get();

                                String accName = manager.getAccountName();
                                String streamName = manager.getStreamName();

                                employee.setManagerId(managerId);
                                employee.setAccountName(accName);
                                employee.setStreamName(streamName);

                            } else {
                                throw new IllegalArgumentException("Invalid manager ID");
                            }
                        }
                        else {
                            throw new IllegalArgumentException("Manager have employees under them");
                        }
                    }
                }
            }
            if(managerId!=null) {
                Optional<Employee> validManager = employeeRepo.findByIdAndManagerIdEqualsZero(managerId);
                if (validManager.isPresent()) {
                    Employee manager = validManager.get();

                    String accName = manager.getAccountName();
                    String streamName = manager.getStreamName();

                    employee.setManagerId(managerId);
                    employee.setAccountName(accName);
                    employee.setStreamName(streamName);

                } else {
                    throw new IllegalArgumentException("Invalid manager ID");
                }
            }

            employeeRepo.save(employee);
        }
        else
        {
            throw new IllegalArgumentException("Invalid employee ID");
        }
    }
}

