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
import java.util.Collections;
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
    public void testAddEmployee_ThrowsException_WhenIdIsProvided() {
        Employee employee = new Employee();
        employee.setEmployeeId(1); // Setting ID should throw exception

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.addEmployee(employee);
        });
        assertEquals("id should be auto generated", exception.getMessage());
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
    public void testAddEmployee_ThrowsException_WhenRequiredFieldsAreMissing() {
        Employee employee = new Employee();
        employee.setEmployeeName("John Doe");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.addEmployee(employee);
        });
        assertEquals("designation,stream name,account name,managerId should have value", exception.getMessage());
    }
    @Test
    public void testAddEmployee_InvalidManagerId() {
        Employee employee = new Employee();
        employee.setEmployeeName("John Doe");
        employee.setDesignation("Associate");
        employee.setStreamName("Development");
        employee.setAccountName("Account1");
        employee.setManagerId(0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.addEmployee(employee);
        });

        assertEquals("Only managers can have managerId 0", exception.getMessage());
    }
    @Test
    public void testAddEmployee_ThrowsException_WhenStreamNotFound() {
        Employee employee = new Employee();
        employee.setEmployeeName("John Doe");
        employee.setDesignation("Associate");
        employee.setStreamName("Development");
        employee.setAccountName("Account1");
        employee.setManagerId(1);

        when(streamRepo.existsByStreamName("Development")).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.addEmployee(employee);
        });
        assertEquals("stream not found", exception.getMessage());
    }
    @Test
    public void testAddEmployee_ThrowsException_WhenAccountNotFound() {
        Employee employee = new Employee();
        employee.setEmployeeName("John Doe");
        employee.setDesignation("Associate");
        employee.setStreamName("Development");
        employee.setAccountName("Account1");
        employee.setManagerId(1);

        when(streamRepo.existsByStreamName("Development")).thenReturn(true);
        when(accountRepo.existsByAccountName("Account1")).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.addEmployee(employee);
        });
        assertEquals("account not found", exception.getMessage());
    }
    @Test
    public void testAddEmployee_ThrowsException_WhenStreamNotInAccount() {
        Employee employee = new Employee();
        employee.setEmployeeName("John Doe");
        employee.setDesignation("Associate"); // Invalid designation
        employee.setStreamName("Development");
        employee.setAccountName("Account2");
        employee.setManagerId(1);
        Stream stream=new Stream();
        stream.setStreamName("Development");
        stream.setAccountName("Account1");
        when(streamRepo.existsByStreamName("Development")).thenReturn(true);
        when(accountRepo.existsByAccountName("Account2")).thenReturn(true);
        when(streamRepo.findByStreamName("Development")).thenReturn(stream);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.addEmployee(employee);
        });
        assertEquals("stream not present in this account", exception.getMessage());
    }
    @Test
    public void testAddEmployee_ThrowsException_WhenStreamAndManagerDoseNotMatch() {
        Employee employee = new Employee();
        employee.setEmployeeName("John Doe");
        employee.setDesignation("Associate"); // Invalid designation
        employee.setStreamName("Development");
        employee.setAccountName("Account1");
        employee.setManagerId(1);
        Stream stream=new Stream();
        stream.setStreamName("Development");
        stream.setAccountName("Account1");
        when(streamRepo.existsByStreamName("Development")).thenReturn(true);
        when(accountRepo.existsByAccountName("Account1")).thenReturn(true);
        when(streamRepo.findByStreamName("Development")).thenReturn(stream);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.addEmployee(employee);
        });
        assertEquals("manager Id and stream does not match", exception.getMessage());
    }
    @Test
    public void testAddEmployee_ThrowsException_WhenInvalidDesignation() {
        Employee employee = new Employee();
        employee.setEmployeeName("John Doe");
        employee.setDesignation("InvalidDesignation"); // Invalid designation
        employee.setStreamName("Development");
        employee.setAccountName("Account1");
        employee.setManagerId(1);
        Stream stream=new Stream();
        stream.setStreamName("Development");
        stream.setAccountName("Account1");
        when(streamRepo.existsByStreamName("Development")).thenReturn(true);
        when(accountRepo.existsByAccountName("Account1")).thenReturn(true);
        when(streamRepo.findByStreamName("Development")).thenReturn(stream);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.addEmployee(employee);
        });
        assertEquals("invalid designation", exception.getMessage());
    }
    @Test
    public void testAddEmployee_ThrowsException_WhenManagerAlreadyExists() {
        Employee employee = new Employee();
        employee.setEmployeeName("Jane Doe");
        employee.setDesignation("Manager");
        employee.setStreamName("Development");
        employee.setAccountName("Account1");
        employee.setManagerId(1);
        Stream stream=new Stream();
        stream.setStreamName("Development");
        stream.setAccountName("Account1");
        when(streamRepo.existsByStreamName("Development")).thenReturn(true);
        when(accountRepo.existsByAccountName("Account1")).thenReturn(true);
        when(streamRepo.findByStreamName("Development")).thenReturn(stream);
        when(employeeRepo.findAllEmployeesByDesignationAndStreamNameAndAccountName("Manager", "Development", "Account1"))
                .thenReturn(List.of(new Employee())); // Simulate existing manager

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.addEmployee(employee);
        });
        assertEquals("Manager already exists in the stream", exception.getMessage());
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
    public void testGetEmployeeService_InValidInput() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.getEmployeeService("");
        });
        assertEquals("enter valid alphabet", exception.getMessage());
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

        employeeService.updateEmployee(1, null, "manager");
        assertEquals(0, existingEmployee.getManagerId()); // Ensure managerId was set correctly
    }

    @Test
    public void testUpdateEmployee_InvalidEmployeeId() {
        when(employeeRepo.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.updateEmployee(1, null, null);
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
            employeeService.updateEmployee(1, 2, "manager");
        });

        assertEquals("A valid Manager is present for the current stream ", exception.getMessage());
    }
    @Test
    public void testUpdateEmployee_ThrowsException_WhenTryingToSetEmployeeWithManagerId() {
        Employee employee = new Employee();
        employee.setEmployeeId(1);
        employee.setDesignation("employee");
        employee.setManagerId(1);

        when(employeeRepo.findById(1)).thenReturn(Optional.of(employee));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.updateEmployee(1, null, "Associate");
        });
        assertEquals("Already an associate!.", exception.getMessage());
    }
    @Test
    public void testUpdateEmployee_ThrowsException_WhenInvalidManagerId() {
        Employee employee = new Employee();
        employee.setEmployeeId(1);
        employee.setDesignation("employee");
        employee.setManagerId(0);
        employee.setStreamName("Development");
        employee.setAccountName("Account1");

        when(employeeRepo.findById(1)).thenReturn(Optional.of(employee));
        when(employeeRepo.findByStreamAndManagerIdEquals("Development", 0)).thenReturn(Optional.empty());
        when(employeeRepo.findByIdAndManagerIdEqualsZero(2)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.updateEmployee(1, 2, null);
        });
        assertEquals("Invalid manager ID", exception.getMessage());
    }
    @Test
    public void testUpdateEmployee_ThrowsException_WhenManagerHasEmployees() {
        Employee employee = new Employee();
        employee.setEmployeeId(1);
        employee.setDesignation("Manager");
        employee.setManagerId(0);
        employee.setStreamName("Development");
        employee.setAccountName("Account1");

        Employee subordinate = new Employee();
        subordinate.setEmployeeId(2);
        subordinate.setManagerId(1);

        when(employeeRepo.findById(1)).thenReturn(Optional.of(employee));
        when(employeeRepo.findAllByManagerId(employee.getEmployeeId())).thenReturn(Collections.singletonList(subordinate));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.updateEmployee(1, 0, "associate");
        });
        assertEquals("Manager have employees under them", exception.getMessage());
    }

    @Test
    public void testUpdateEmployee_UpdatesManagerIdAndAccountName() {
        Employee employee = new Employee();
        employee.setEmployeeId(1);
        employee.setDesignation("employee");
        employee.setManagerId(0);
        employee.setStreamName("Development");
        employee.setAccountName("Account1");

        Employee manager = new Employee();
        manager.setEmployeeId(2);
        manager.setStreamName("Development");
        manager.setAccountName("Account1");

        when(employeeRepo.findById(1)).thenReturn(Optional.of(employee));
        when(employeeRepo.findByStreamAndManagerIdEquals("Development", 0)).thenReturn(Optional.empty());
        when(employeeRepo.findByIdAndManagerIdEqualsZero(2)).thenReturn(Optional.of(manager));

        employeeService.updateEmployee(1, 2, null);

        assertEquals(2, employee.getManagerId());
        assertEquals("Account1", employee.getAccountName());
        assertEquals("Development", employee.getStreamName());
        verify(employeeRepo).save(employee);
    }

}

