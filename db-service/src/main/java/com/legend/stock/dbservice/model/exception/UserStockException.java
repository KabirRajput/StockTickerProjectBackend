package com.legend.stock.dbservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UserStockException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8488326437940514973L;

	public UserStockException(String message) {
		super(message);
	}
}
