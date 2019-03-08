package com.legend.stock.dbservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UserBalanceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7860544812050103662L;

	public UserBalanceException(String message) {
		super(message);
	}
}
