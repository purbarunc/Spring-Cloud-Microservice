package com.codex.ecom.inventory.exception;

public class InventoryServiceException extends RuntimeException{
	private static final long serialVersionUID = -2063561005776817020L;

	public InventoryServiceException() {
		super();
	}

	public InventoryServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InventoryServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public InventoryServiceException(String message) {
		super(message);
	}

	public InventoryServiceException(Throwable cause) {
		super(cause);
	}
}
