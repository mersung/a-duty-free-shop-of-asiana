package com.example.project.exception;

import com.example.project.exception.handler.ErrorCode;

import lombok.Getter;

@Getter
public class NoProductStockException extends RuntimeException {
	// 실행예외시 RuntimeException 상속, 일반예외시 Excpetion, 커스터마이징 하므로 RuntimeException
	private ErrorCode errorCode;
	
	public NoProductStockException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public NoProductStockException() {
		super("해당 상품은 재고가 없습니다.");
	}
}
