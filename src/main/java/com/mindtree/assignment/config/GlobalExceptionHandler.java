package com.mindtree.assignment.config;

import java.util.InputMismatchException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mindtree.assignment.controller.CartController;
import com.mindtree.assignment.controller.CustomerController;
import com.mindtree.assignment.exception.MyApplicationException;


@RestControllerAdvice(assignableTypes = { CustomerController.class,CartController.class })

public class GlobalExceptionHandler {
	 @ExceptionHandler({InputMismatchException.class,MyApplicationException.class})
	    public ResponseEntity<ApiResponse> MyApplicationExceptionhandler(Exception e, Throwable cause) {
		 
	    	ApiResponse response = new ApiResponse("Exception Message", true, e.getMessage(), HttpStatus.BAD_REQUEST);
	        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	    }

}
