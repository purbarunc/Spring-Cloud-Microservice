package com.codex.ecom.order.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryRequest {
	private String productId;
	private int units;
}
