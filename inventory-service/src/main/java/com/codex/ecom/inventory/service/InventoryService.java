package com.codex.ecom.inventory.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.codex.ecom.inventory.dto.InventoryRequest;
import com.codex.ecom.inventory.dto.ProductRequest;
import com.codex.ecom.inventory.exception.InventoryServiceException;
import com.codex.ecom.inventory.model.Inventory;
import com.codex.ecom.inventory.repository.InventoryRepository;
import com.codex.ecom.inventory.response.Product;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InventoryService {
	@Autowired
	private InventoryRepository inventoryRepository;
	@Autowired
	private ProductService productService;
	private static Date CURRENT_DATETIME = new Date(System.currentTimeMillis());

	public Inventory save(InventoryRequest inventoryRequest) {
		Inventory inventory = Inventory.builder().createdDate(CURRENT_DATETIME).updatedDate(CURRENT_DATETIME)
				.productId(inventoryRequest.getProductId()).units(inventoryRequest.getUnits()).build();
		return inventoryRepository.save(inventory);
	}

	public Inventory getInventory(String productId) {
		log.info("Getting Stock Units for productId: {}",productId);
		return inventoryRepository.findByProductId(productId).orElseThrow(() -> new InventoryServiceException(
				String.format("Product for the id %s is not available", productId)));
	}

	public Inventory updateStocks(InventoryRequest inventoryRequest) {
		Inventory currentInventory = getInventory(inventoryRequest.getProductId());
		Inventory inventory = Inventory.builder().skuCode(currentInventory.getSkuCode()).updatedDate(CURRENT_DATETIME)
				.productId(inventoryRequest.getProductId())
				.units(currentInventory.getUnits() - inventoryRequest.getUnits()).build();
		log.info("Deducting Stock Units for productId: {} with sku-code: {}",inventory.getProductId(),inventory.getSkuCode());
		return inventoryRepository.save(inventory);
	}

	public void updateProductStatus(ProductRequest productRequest) {
		ResponseEntity<Product> productResponse = productService.updateProduct(productRequest);
		if(productResponse.getStatusCode()!= HttpStatus.OK) {
			throw new InventoryServiceException("Unable to update product");
		}
	}
}
