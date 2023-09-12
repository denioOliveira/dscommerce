package com.devsuperior.dscommerce.controlles.handlers;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.dscommerce.dto.CustomError;
import com.devsuperior.dscommerce.dto.ValidaError;
import com.devsuperior.dscommerce.exeptions.DataBaseException;
import com.devsuperior.dscommerce.exeptions.ResouceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {
	
	@ExceptionHandler(ResouceNotFoundException.class)
	public ResponseEntity<CustomError> resourceNotFound(ResouceNotFoundException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND;
		CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
		return  ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler( DataBaseException.class)
	public ResponseEntity<CustomError> dataBase(DataBaseException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.BAD_REQUEST;
		CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
		return  ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler( MethodArgumentNotValidException.class)
	public ResponseEntity<CustomError> methodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		ValidaError err = new ValidaError(Instant.now(), status.value(), "Dados de validação", request.getRequestURI());
		for(FieldError f : e.getBindingResult().getFieldErrors()) {
			err.addError(f.getField(), f.getDefaultMessage()); 
		}
		return  ResponseEntity.status(status).body(err);
	}

}
