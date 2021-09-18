package com.codex.ecom.order.dto;

import java.util.List;

import com.codex.ecom.order.model.OrderItems;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
	private List<OrderItems> orderItems;
	private String emailId;
}
