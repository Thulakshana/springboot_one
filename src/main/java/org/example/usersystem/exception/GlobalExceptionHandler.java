package org.example.usersystem.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            InvalidUsernameException.class)
    public ResponseEntity<String>
    usernameException(
            InvalidUsernameException ex){

        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }

    @ExceptionHandler(
            InvalidPasswordException.class)
    public ResponseEntity<String>
    passwordException(
            InvalidPasswordException ex){

        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }
}