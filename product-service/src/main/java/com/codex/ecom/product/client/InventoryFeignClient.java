package com.codex.ecom.product.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.codex.ecom.product.dto.InventoryRequest;
import com.codex.ecom.product.model.Inventory;

@FeignClient(name = "inventory-service")
public interface InventoryFeignClient {
	@PostMapping("/inventory")
	ResponseEntity<Inventory> createStockUnits(@RequestBody InventoryRequest inventoryRequest);
}
