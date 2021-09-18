package com.codex.ecom.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.codex.ecom.order.client.ProductFeignClient;
import com.codex.ecom.order.model.OrderItems;
import com.codex.ecom.order.response.Product;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {
	@Autowired
	private ProductFeignClient productFeignClient;

	public ResponseEntity<Product> getProductResponse(OrderItems order) {
		log.info("Calling product-service for productId: {}", order.getProductId());
		return productFeignClient.getProduct(order.getProductId());
	}
}
