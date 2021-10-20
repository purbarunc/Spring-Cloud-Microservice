package com.codex.ecom.product.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
	private String productId;
	private String name;
	private String category;
	private BigDecimal price;
	private int units;
	Boolean activeIndicator;
}
