package com.codex.ecom.product.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codex.ecom.product.dto.ProductRequest;
import com.codex.ecom.product.exception.ProductNotFoundException;
import com.codex.ecom.product.model.Product;
import com.codex.ecom.product.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;
	private static Date CURRENT_DATETIME = new Date(System.currentTimeMillis());

	public List<Product> findAllProducts() {
		return productRepository.findAll();
	}

	public Product findById(String productId) {
		return productRepository.findById(productId).orElseThrow(
				() -> new ProductNotFoundException(String.format("Product for the id %s is not available", productId)));
	}

	public Product create(ProductRequest productRequest) {
		Product product = Product.builder().createdDate(CURRENT_DATETIME).updatedDate(CURRENT_DATETIME)
				.activeIndicator(true).name(productRequest.getName()).category(productRequest.getCategory())
				.price(productRequest.getPrice()).build();
		return productRepository.insert(product);
	}

	public Product update(ProductRequest productRequest) {
		Product productForUpdate = findById(productRequest.getProductId());
		if (productRequest.getActiveIndicator() != null) {
			productForUpdate.setActiveIndicator(productRequest.getActiveIndicator());
		}
		if (productRequest.getName() != null) {
			productForUpdate.setName(productRequest.getName());
		}
		if (productRequest.getCategory() != null) {
			productForUpdate.setCategory(productRequest.getCategory());
		}
		if (productRequest.getPrice() != null) {
			productForUpdate.setPrice(productRequest.getPrice());
		}
		productForUpdate.setUpdatedDate(CURRENT_DATETIME);
		return productRepository.save(productForUpdate);
	}

	public void delete(String productId) {
		productRepository.deleteById(productId);
	}
}
