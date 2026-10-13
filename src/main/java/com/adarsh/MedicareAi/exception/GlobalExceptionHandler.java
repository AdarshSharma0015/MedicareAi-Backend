package com.adarsh.MedicareAi.exception;

import com.adarsh.MedicareAi.dto.LoginResponse;
import com.adarsh.MedicareAi.dto.RegisterResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<RegisterResponse> handleEmailAlreadyExists(EmailAlreadyExistsException e) {

        RegisterResponse response = new RegisterResponse(false, e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AdminRegistrationException.class)
    public ResponseEntity<RegisterResponse> handleAdminRegistration(AdminRegistrationException e) {

        RegisterResponse response = new RegisterResponse(false, e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // Exception in Validation of RegisterReq
    public ResponseEntity<RegisterResponse> handleValidationErrors(MethodArgumentNotValidException e) {

        String message = e.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        RegisterResponse response = new RegisterResponse(false, message);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<LoginResponse> handleEmailNotFound(EmailNotFoundException e) {

        LoginResponse response = new LoginResponse(false, e.getMessage(), null);

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
// NULL token means login failed so No token Generated

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<LoginResponse> handleInvalidPassword(InvalidPasswordException e) {

        LoginResponse response = new LoginResponse(false, e.getMessage(), null);

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<String> handlePatientNotFound(PatientNotFoundException e) {

        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException e) {

        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);

    }



    @ExceptionHandler(InvalidPatientRoleException.class)
    public ResponseEntity<String> handleInvalidPatientRole(InvalidPatientRoleException e) {

        return new ResponseEntity<>(e.getMessage(),HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(PatientProfileAlreadyExistsException.class)
    public ResponseEntity<String> handlePatientProfileAlreadyExists(PatientProfileAlreadyExistsException e) {

        return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);

    }


}
