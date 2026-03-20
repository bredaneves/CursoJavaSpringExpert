package com.devsuperior.bds02.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.bds02.services.exceptions.DatabaseException;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Void> resourceNotFound(ResourceNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
    
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<Void> databaseException(DatabaseException e) {
        return ResponseEntity.badRequest().build();
    }
}
