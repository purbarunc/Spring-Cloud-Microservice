package com.codex.ecom.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.codex.ecom.order.client.InventoryFeignClient;
import com.codex.ecom.order.dto.InventoryRequest;
import com.codex.ecom.order.response.Inventory;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InventoryService {
	@Autowired
	private InventoryFeignClient inventoryFeignClient;

	ResponseEntity<Inventory> deductStockUnits(InventoryRequest inventoryRequest) {
		log.info("Calling inventory-service for updating stock units: {}", inventoryRequest);
		return inventoryFeignClient.deductStockUnits(inventoryRequest);
	}

	ResponseEntity<Inventory> getProductUnits(String productId) {
		log.info("Calling inventory-service for fetching current stocks for productId: {}", productId);
		return inventoryFeignClient.getProductUnits(productId);
	}
}
