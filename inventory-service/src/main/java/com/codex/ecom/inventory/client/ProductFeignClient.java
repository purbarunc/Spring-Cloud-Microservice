package com.codex.ecom.inventory.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.codex.ecom.inventory.configuration.FeignConfiguration;
import com.codex.ecom.inventory.dto.ProductRequest;
import com.codex.ecom.inventory.response.Product;

@FeignClient(name = "product-service", configuration = FeignConfiguration.class)
public interface ProductFeignClient {
	@PutMapping("/product")
	ResponseEntity<Product> updateProduct(@RequestBody ProductRequest productRequest);
}
