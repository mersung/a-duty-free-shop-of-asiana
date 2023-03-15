package com.example.project.exception;

import com.example.project.exception.handler.ErrorCode;

import lombok.Getter;

@Getter
public class NoProductException extends RuntimeException{
	
	private ErrorCode errorCode;
	
	public NoProductException(String message, ErrorCode errorCode) {
		
		super(message);
		this.errorCode = errorCode;
		
	}
	
	public NoProductException() {
		super("존재하지 않는 상품입니다.");
	}
}
