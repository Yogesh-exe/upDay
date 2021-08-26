package com.upday.article.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
	
	private final HttpStatus status;
	
	private final String errorMessage;
	
	private final String finalMessage;
	
	public BusinessException(HttpStatus status, String errorMessage) {		
		this(status,errorMessage,new Object());
	}

	public BusinessException(HttpStatus status, String errorMessage, Object... fields) {
		this.finalMessage = String.format(errorMessage, fields);
		this.status=status;
		this.errorMessage=errorMessage;
	}

}
