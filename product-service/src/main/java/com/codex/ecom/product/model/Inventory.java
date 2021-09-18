package com.codex.ecom.product.model;

import java.util.Date;

import lombok.Data;

@Data
public class Inventory {
	private String skuCode;
	private String productId;
	private Integer units;
	private Date createdDate;
	private Date updatedDate;
}
