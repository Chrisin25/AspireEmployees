package com.aspire.employee.handlers;

import com.aspire.employee.response.ResponseMessage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlersTest {

    private final GlobalExceptionHandlers exceptionHandlers = new GlobalExceptionHandlers();

    @Test
    public void testHandleIllegalArgumentException() {
        IllegalArgumentException exception = new IllegalArgumentException("Invalid argument");

        ResponseEntity<ResponseMessage> response = exceptionHandlers.handleIllegalArgumentException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid argument", response.getBody().getMessage());
    }

    @Test
    public void testHandleGenericException() {
        Exception exception = new Exception("An unexpected error occurred");

        ResponseEntity<ResponseMessage> response = exceptionHandlers.handleGenericException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred", response.getBody().getMessage());
    }

    @Test
    public void testHandleValidationExceptions() {
        MethodArgumentNotValidException exception = Mockito.mock(MethodArgumentNotValidException.class);
        Mockito.when(exception.getMessage()).thenReturn("Validation failed");

        ResponseEntity<ResponseMessage> response = exceptionHandlers.handleValidationExceptions(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Validation failed: Validation failed", response.getBody().getMessage());
    }

    @Test
    public void testHandleArgumentMismatchExceptions() {
        MethodArgumentTypeMismatchException exception = new MethodArgumentTypeMismatchException(
                null, null, null, null, null);

        ResponseEntity<ResponseMessage> response = exceptionHandlers.handleArgumentMismatchExceptions(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Input cannot be empty", response.getBody().getMessage());
    }
}
