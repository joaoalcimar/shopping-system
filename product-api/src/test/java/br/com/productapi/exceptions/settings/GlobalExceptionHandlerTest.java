package br.com.productapi.exceptions.settings;

import br.com.productapi.exceptions.AuthenticationException;
import br.com.productapi.exceptions.EmptyStringException;
import br.com.productapi.exceptions.NotFoundException;
import br.com.productapi.exceptions.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class GlobalExceptionHandlerTest {

    @Test
    public void testHandleEmptyStringException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        EmptyStringException emptyStringException = new EmptyStringException("Empty string");
        ResponseEntity<?> responseEntity = handler.handleEmptyStringException(emptyStringException);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Empty string", ((ExceptionDetails) responseEntity.getBody()).getMessage());
    }

    @Test
    public void testHandleValidationException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        ValidationException validationException = new ValidationException("Validation failed");
        ResponseEntity<?> responseEntity = handler.handleValidationException(validationException);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Validation failed", ((ExceptionDetails) responseEntity.getBody()).getMessage());
    }

    @Test
    public void testHandleNotFoundException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        NotFoundException notFoundException = new NotFoundException("Resource not found");
        ResponseEntity<?> responseEntity = handler.handleNotFoundException(notFoundException);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Resource not found", ((ExceptionDetails) responseEntity.getBody()).getMessage());
    }

    @Test
    public void testHandleAuthenticationException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        AuthenticationException authenticationException = new AuthenticationException("Unauthorized");
        ResponseEntity<?> responseEntity = handler.handleAuthenticationException(authenticationException);

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals("Unauthorized", ((ExceptionDetails) responseEntity.getBody()).getMessage());
    }
}

