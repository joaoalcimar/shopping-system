package br.com.productapi.exceptions.settings;

import br.com.productapi.exceptions.AuthenticationException;
import br.com.productapi.exceptions.EmptyStringException;
import br.com.productapi.exceptions.NotFoundException;
import br.com.productapi.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmptyStringException.class)
    public ResponseEntity<?> handleEmptyStringException(EmptyStringException emptyStringException){

        ExceptionDetails details = new ExceptionDetails();
        details.setStatus(HttpStatus.BAD_REQUEST.value());
        details.setMessage(emptyStringException.getMessage());

        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException validationException){

        ExceptionDetails details = new ExceptionDetails();
        details.setStatus(HttpStatus.BAD_REQUEST.value());
        details.setMessage(validationException.getMessage());

        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException notFoundException){

        ExceptionDetails details = new ExceptionDetails();
        details.setStatus(HttpStatus.NOT_FOUND.value());
        details.setMessage(notFoundException.getMessage());

        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException authenticationException){

        ExceptionDetails details = new ExceptionDetails();
        details.setStatus(HttpStatus.UNAUTHORIZED.value());
        details.setMessage(authenticationException.getMessage());

        return new ResponseEntity<>(details, HttpStatus.UNAUTHORIZED);
    }
}
