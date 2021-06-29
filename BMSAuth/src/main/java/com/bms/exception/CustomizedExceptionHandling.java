package com.bms.exception;

import java.time.LocalDateTime;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bms.model.MessageResponse;

import lombok.extern.slf4j.Slf4j;

/**Exception class*/
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class CustomizedExceptionHandling  extends ResponseEntityExceptionHandler {
	
	/**
	 * @param ex
	 * @return
	 */
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<Object> handleUnauthorizedExceptions(UnauthorizedException ex) {

		log.error("Unauthorized request");
		return ResponseEntity.badRequest().body(new MessageResponse("Unauthorized request. Login again...",LocalDateTime.now()));
	}
	
	/**
	 * @param ex
	 * @return
	 */
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<Object> usernameNotFoundExceptions(UsernameNotFoundException ex) {

		log.error("User ID not available...............");
		return ResponseEntity.badRequest().body(new MessageResponse("User ID not available",LocalDateTime.now()));
	}
	
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<Object> handleNullPointerExceptions(NullPointerException ex) {

		log.error("User ID not available...............");
		return ResponseEntity.badRequest().body(new MessageResponse("User ID not available",LocalDateTime.now()));
	}
	
	
}
