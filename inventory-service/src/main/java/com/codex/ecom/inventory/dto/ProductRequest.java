package com.codex.ecom.inventory.dto;

import lombok.Data;

@Data
public class ProductRequest {
	String productId;
	Boolean activeIndicator;
}
