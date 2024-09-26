package com.aspire.employee.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.aspire.employee.models.Stream;
import com.aspire.employee.repository.AccountRepo;
import com.aspire.employee.repository.EmployeeRepo;
import com.aspire.employee.repository.StreamRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.aspire.employee.models.Employee;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepo employeeRepo;

    @Mock
    private AccountRepo accountRepo;

    @Mock
    private StreamRepo streamRepo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddEmployee_ValidAssociate() {
        Employee employee = new Employee();
        employee.setEmployeeName("John Doe");
        employee.setDesignation("Associate");
        employee.setStreamName("Development");
        employee.setAccountName("Account1");
        employee.setManagerId(1);
        Stream stream=new Stream();
        stream.setStreamName("Development");
        stream.setAccountName("Account1");
        when(streamRepo.existsByStreamName("Development")).thenReturn(true);
        when(accountRepo.existsByAccountName("Account1")).thenReturn(true);
        when(employeeRepo.existsByEmployeeIdAndDesignationAndStreamName(1, "Manager", "Development")).thenReturn(true);
        when(employeeRepo.save(any(Employee.class))).thenAnswer(invocation -> {
            Employee savedEmployee = invocation.getArgument(0);
            savedEmployee.setEmployeeId(1); // Mock auto-generated ID
            return savedEmployee;
        });
        when(streamRepo.findByStreamName("Development")).thenReturn(stream);
        int result = employeeService.addEmployee(employee);
        assertEquals(employee.getEmployeeId(), result);
    }

    @Test
    public void testAddEmployee_InvalidEmployeeName() {
        Employee employee = new Employee();
        employee.setEmployeeName("");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.addEmployee(employee);
        });

        assertEquals("specify valid employeeName", exception.getMessage());
    }

    @Test
    public void testGetEmployeeService_ValidInput() {
        List<Employee> employees = new ArrayList<>();
        Employee emp1 = new Employee();
        emp1.setEmployeeName("John Doe");
        employees.add(emp1);

        when(employeeRepo.findAllByEmployeeNameStartingWith("J")).thenReturn(employees);

        List<Employee> result = employeeService.getEmployeeService("J");
        assertEquals(employees, result);
    }

    @Test
    public void testGetEmployeeService_NoEmployeesFound() {
        when(employeeRepo.findAllByEmployeeNameStartingWith("A")).thenReturn(new ArrayList<>());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.getEmployeeService("A");
        });

        assertEquals("no employee name starts with A", exception.getMessage());
    }

    @Test
    public void testGetAllStreams_NoStreamsPresent() {
        when(streamRepo.findAll()).thenReturn(new ArrayList<>());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.getAllStreams();
        });

        assertEquals("no streams present", exception.getMessage());
    }

    @Test
    public void testUpdateEmployee_ValidManagerUpdate() {
        Employee existingEmployee = new Employee();
        existingEmployee.setEmployeeId(1);
        existingEmployee.setStreamName("Development");
        existingEmployee.setManagerId(0);

        when(employeeRepo.findById(1)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepo.findByStreamAndManagerIdEquals("Development", 0)).thenReturn(Optional.empty());
        when(employeeRepo.save(existingEmployee)).thenReturn(existingEmployee);

        employeeService.updateEmployee(1, null, "manager", null);
        assertEquals(0, existingEmployee.getManagerId()); // Ensure managerId was set correctly
    }

    @Test
    public void testUpdateEmployee_InvalidEmployeeId() {
        when(employeeRepo.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.updateEmployee(1, null, null, null);
        });

        assertEquals("Invalid employee ID", exception.getMessage());
    }

    @Test
    public void testUpdateEmployee_InvalidManager() {
        Employee existingEmployee = new Employee();
        existingEmployee.setEmployeeId(1);
        existingEmployee.setStreamName("Development");

        when(employeeRepo.findById(1)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepo.findByStreamAndManagerIdEquals("Development", 0)).thenReturn(Optional.of(new Employee()));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.updateEmployee(1, 2, "manager", null);
        });

        assertEquals("A valid Manager is present for the current stream ", exception.getMessage());
    }


}

