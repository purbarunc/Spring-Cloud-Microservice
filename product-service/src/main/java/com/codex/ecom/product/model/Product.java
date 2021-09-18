package com.codex.ecom.product.model;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class Product {
	@Id
	private String productId;
	private String name;
	private String category;
	private BigDecimal price;
	private boolean activeIndicator;
	private Date createdDate;
	private Date updatedDate;
}
