package com.example.project.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.project.exception.NoProductException;
import com.example.project.exception.NoProductStockException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestControllerAdvice //모든 컨트롤러에서 발생하는 exception처리
public class GlobalExceptionHandler {
	
	@ExceptionHandler(NoProductStockException.class)
	public ResponseEntity<ErrorResponse> handleNoProductStockException(NoProductStockException ex){
		log.error("handleNoProductStockException",ex);
		ErrorResponse response = new ErrorResponse(ex.getErrorCode());
		response.setMessage(ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
	}
	

	@ExceptionHandler(NoProductException.class)
	public ResponseEntity<ErrorResponse> handleNoProductException(NoProductException ex){
		log.error("handleNoProductException",ex);
		ErrorResponse response = new ErrorResponse(ex.getErrorCode());
		response.setMessage(ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
	}
}
