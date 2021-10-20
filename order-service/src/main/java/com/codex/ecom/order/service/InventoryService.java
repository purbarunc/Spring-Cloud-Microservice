package com.codex.ecom.order.service;

import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreaker;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.http.HttpStatus;
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
	@Autowired
	private Resilience4JCircuitBreakerFactory circuitBreakerFactory;
	@Autowired
	private ExecutorService traceableExecutorService;

	ResponseEntity<Inventory> deductStockUnits(InventoryRequest inventoryRequest) {
		log.info("Calling inventory-service for updating stock units: {}", inventoryRequest);
		return inventoryFeignClient.deductStockUnits(inventoryRequest);
	}

	ResponseEntity<Inventory> getProductUnits(String productId) {
		log.info("Calling inventory-service for fetching current stocks for productId: {}", productId);
		circuitBreakerFactory.configureExecutorService(traceableExecutorService);
		Resilience4JCircuitBreaker circuitBreaker = circuitBreakerFactory.create("inventory");
		Supplier<ResponseEntity<Inventory>> supplier = () -> inventoryFeignClient.getProductUnits(productId);
		return circuitBreaker.run(supplier, throwable -> fallbackLogic());
	}

	private ResponseEntity<Inventory> fallbackLogic() {
		log.info("inventory-service is down or not available, executing fallback logic");
		Inventory inventory = new Inventory();
		inventory.setUnits(0);
		return new ResponseEntity<Inventory>(inventory, HttpStatus.OK);
	}
}
