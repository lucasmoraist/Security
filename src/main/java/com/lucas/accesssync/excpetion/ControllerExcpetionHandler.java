package com.lucas.accesssync.excpetion;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lucas.accesssync.excpetion.dto.ExcpetionDTO;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ControllerExcpetionHandler {
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity threatDuplicateEntry(DataIntegrityViolationException exception){
        ExcpetionDTO dto = new ExcpetionDTO("Número de documento já cadastrado", "400");
        return ResponseEntity.badRequest().body(dto);   
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity threat404(EntityNotFoundException exception){
        return ResponseEntity.notFound().build();   
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity threatGeneralException(Exception exception){
        ExcpetionDTO dto = new ExcpetionDTO(exception.getMessage(), "500");
        return ResponseEntity.internalServerError().body(dto);   
    }
}
