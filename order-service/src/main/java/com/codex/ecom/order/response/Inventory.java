package com.codex.ecom.order.response;

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
