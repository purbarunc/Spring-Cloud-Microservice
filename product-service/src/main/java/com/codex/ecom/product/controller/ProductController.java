package com.codex.ecom.product.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecom.product.client.InventoryFeignClient;
import com.codex.ecom.product.dto.InventoryRequest;
import com.codex.ecom.product.dto.ProductRequest;
import com.codex.ecom.product.dto.ProductResponse;
import com.codex.ecom.product.model.Inventory;
import com.codex.ecom.product.model.Product;
import com.codex.ecom.product.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@Autowired
	private InventoryFeignClient inventoryFeignClient;

	@GetMapping("/all")
	public ResponseEntity<List<Product>> getAllProducts() {
		return new ResponseEntity<>(productService.findAllProducts(), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<Product> getProduct(@RequestParam String productId) {
		return new ResponseEntity<>(productService.findById(productId), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<ProductResponse> createProduct(@NotNull @RequestBody ProductRequest productRequest,
			@RequestParam int units) {
		String productId = productService.create(productRequest).getProductId();
		InventoryRequest inventoryRequest = InventoryRequest.builder().productId(productId).units(units).build();
		ResponseEntity<Inventory> inventory = inventoryFeignClient.createStockUnits(inventoryRequest);
		if (inventory.getStatusCode() == HttpStatus.CREATED) {
			return new ResponseEntity<>(
					new ProductResponse(String.format("Product id=%s has been created with inventory skucode=%s",
							productId, inventory.getBody().getSkuCode())),
					HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(new ProductResponse("Inventory insertion faild"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PutMapping
	public ResponseEntity<Product> updateProduct(@NotNull @RequestBody ProductRequest productRequest) {
		return new ResponseEntity<>(productService.update(productRequest), HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<ProductResponse> deleteProduct(@RequestParam String productId) {
		productService.delete(productId);
		return new ResponseEntity<>(new ProductResponse("Product has been deleted."), HttpStatus.OK);
	}
}
