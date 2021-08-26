package com.upday.article.exception.handler;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.upday.article.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice(basePackages ="com.upday.article.controller")
@Slf4j
public class ArticleExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorDetails> handleBusinessException(BusinessException ex, WebRequest request){
		log.error(ex.getFinalMessage());
		ErrorDetails errorBody = new ErrorDetails(ex.getFinalMessage());
		return new ResponseEntity<>(errorBody, ex.getStatus());

	}

	
	@Override
	  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	                                                                HttpHeaders headers, HttpStatus status,
	                                                                WebRequest request) {
		
	    String errorMessage = ex.getBindingResult().getFieldErrors().stream()
	                 .map(DefaultMessageSourceResolvable::getDefaultMessage)
	                 .findFirst()
	                 .orElse(ex.getMessage());
	    log.error(errorMessage);
	    ErrorDetails errorBody = new ErrorDetails(errorMessage);

		return new ResponseEntity<>(errorBody,HttpStatus.BAD_REQUEST);
	  }
}
