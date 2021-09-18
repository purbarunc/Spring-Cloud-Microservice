package com.codex.ecom.inventory.dto;

import lombok.Data;

@Data
public class InventoryRequest {
	private String productId;
	private int units;
}
