package com.codex.ecom.order.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "orders")
public class Order {
	@Id
	private String orderId;
	private List<OrderItems> orderItems;
	private Date createdDate;
	private Date updatedDate;
	private BigDecimal invoiceAmt;
}
