package com.codex.ecom.order.exception;

public class OrderServiceException extends RuntimeException {
	private static final long serialVersionUID = 2055946906343748178L;

	public OrderServiceException() {
		super();
	}

	public OrderServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public OrderServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public OrderServiceException(String message) {
		super(message);
	}

	public OrderServiceException(Throwable cause) {
		super(cause);
	}
}
