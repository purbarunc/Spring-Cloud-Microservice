package com.codex.ecom.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.codex.ecom.order.dto.InventoryRequest;
import com.codex.ecom.order.response.Inventory;

@FeignClient(name="inventory-service")
public interface InventoryFeignClient {
	@GetMapping("/inventory")
	ResponseEntity<Inventory> getProductUnits(@RequestParam("productId") String productId);
	
	@PutMapping("/inventory")
	ResponseEntity<Inventory> deductStockUnits(@RequestBody InventoryRequest inventoryRequest);
}
