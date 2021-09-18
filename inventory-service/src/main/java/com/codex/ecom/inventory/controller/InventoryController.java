package com.codex.ecom.inventory.controller;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecom.inventory.dto.InventoryRequest;
import com.codex.ecom.inventory.dto.ProductRequest;
import com.codex.ecom.inventory.model.Inventory;
import com.codex.ecom.inventory.service.InventoryService;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
	@Autowired
	private InventoryService inventoryService;
	

	@PostMapping
	public ResponseEntity<Inventory> createStockUnits(@NotNull @RequestBody InventoryRequest inventoryRequest) {
		Inventory inventory = inventoryService.save(inventoryRequest);
		return new ResponseEntity<>(inventory, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<Inventory> getProductUnits(@RequestParam String productId) {
		return new ResponseEntity<>(inventoryService.getInventory(productId), HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<Inventory> deductStockUnits(@RequestBody InventoryRequest inventoryRequest) {
		Inventory inventory=inventoryService.updateStocks(inventoryRequest);
		if(inventory.getUnits()==0) {
			ProductRequest productRequest=new ProductRequest();
			productRequest.setProductId(inventory.getProductId());
			productRequest.setActiveIndicator(false);
			inventoryService.updateProductStatus(productRequest);
		}
		return new ResponseEntity<>(inventory, HttpStatus.OK);
	}
}
