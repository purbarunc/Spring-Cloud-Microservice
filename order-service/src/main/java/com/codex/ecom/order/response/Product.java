package com.codex.ecom.order.response;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class Product {
	private String productId;
	private String name;
	private String category;
	private BigDecimal price;
	private boolean activeIndicator;
	private Date createdDate;
	private Date updatedDate;
}
