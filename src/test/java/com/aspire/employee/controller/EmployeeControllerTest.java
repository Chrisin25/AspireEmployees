package com.aspire.employee.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.http.MediaType.*;

import com.aspire.employee.models.Stream;
import com.aspire.employee.response.ResponseMessage;
import com.aspire.employee.models.Employee;
import com.aspire.employee.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

public class EmployeeControllerTest {

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    public void testAddEmployee() throws Exception {

        int generatedId = 1;

        when(employeeService.addEmployee(any(Employee.class))).thenReturn(generatedId);

        mockMvc.perform(post("/api/v1/employee")
                        .contentType(APPLICATION_JSON)
                        .content("{ \"name\": \"John Doe\" }")) // Replace with actual employee JSON
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Successfully created"))
                .andExpect(jsonPath("$.id").value(generatedId));
    }

    @Test
    public void testGetEmployees() throws Exception {
        List<Employee> employees = Arrays.asList(new Employee(), new Employee());

        when(employeeService.getEmployeeService("J")).thenReturn(employees);

        mockMvc.perform(get("/api/v1/employees")
                        .param("startsWith", "J"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(employees.size()));
    }

    @Test
    public void testGetStreams() throws Exception {
        List<Stream> streams = Arrays.asList(new Stream(), new Stream());

        when(employeeService.getAllStreams()).thenReturn(streams);

        mockMvc.perform(get("/api/v1/streams"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(streams.size()));
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessage("Successfully updated employee details");

        doNothing().when(employeeService).updateEmployee(anyInt(), anyInt(), anyString());

        mockMvc.perform(put("/api/v1/employee")
                        .param("employeeId", "1")
                        .param("managerId", "2")
                        .param("accountName", "Account1")
                        .param("designation", "Developer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Successfully updated employee details"));
    }
}

