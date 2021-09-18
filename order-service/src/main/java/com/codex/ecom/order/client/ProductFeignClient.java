package com.codex.ecom.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codex.ecom.order.configuration.FeignConfiguration;
import com.codex.ecom.order.response.Product;

@FeignClient(name = "product-service", configuration = FeignConfiguration.class)
public interface ProductFeignClient {
	@GetMapping("/product")
	ResponseEntity<Product> getProduct(@RequestParam("productId") String productId);
}
