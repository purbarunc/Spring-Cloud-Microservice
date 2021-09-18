package com.codex.ecom.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.codex.ecom.inventory.client.ProductFeignClient;
import com.codex.ecom.inventory.dto.ProductRequest;
import com.codex.ecom.inventory.response.Product;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {
	@Autowired
	private ProductFeignClient productFeignClient;

	public ResponseEntity<Product> updateProduct(ProductRequest productRequest) {
		log.info("Calling product-service for updating product: {}", productRequest);
		return productFeignClient.updateProduct(productRequest);
	}
}
