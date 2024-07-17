package com.cg.onlinesweetmart.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	// handle API exceptions
	
	@ExceptionHandler(SweetMartAPIException.class)
	public ResponseEntity<ErrorDetails> handleSweetMartAPIException(SweetMartAPIException exception, WebRequest webRequest) {
		
		ErrorDetails errorDetails = new ErrorDetails(
				LocalDateTime.now(),
				exception.getMessage(),
				webRequest.getDescription(false),
				exception.getStatus()
		);
		
		
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	// User input valid exception
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException methodArgumentNotValidException) {
		
        Map<String, String> errors = new HashMap<>();
        
        methodArgumentNotValidException.getBindingResult()
        								.getAllErrors()
        								.forEach((error) -> {
								            String fieldName = ((FieldError) error).getField();
								            String errorMessage = error.getDefaultMessage();
								            errors.put(fieldName, errorMessage);
							        });
        
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
	
}
