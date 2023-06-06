package com.ecommerce.exceptions;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.ecommerce.model.OrderResponseVO;

@ControllerAdvice
public class OrderExceptionHandler{
	
	@Autowired
	OrderResponseVO orderResponseVO;
	
	// Exception handling
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		orderResponseVO.setTimeStamp(System.currentTimeMillis());
		orderResponseVO.setMessage("Order input validations failed");
		
		List<String> errors = ex.getBindingResult().getFieldErrors().stream()
							.map(x -> x.getDefaultMessage())
							.collect(Collectors.toList());
		orderResponseVO.setErrors(errors);
		
		
		return new ResponseEntity<>(orderResponseVO, status);
	}
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleExceptions(Exception ex) {
    	
    	orderResponseVO.setTimeStamp(System.currentTimeMillis());
		orderResponseVO.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(orderResponseVO);
    }
	
	
	

}
