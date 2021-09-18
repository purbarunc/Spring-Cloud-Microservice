package com.codex.ecom.order.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.codex.ecom.order.dto.InventoryRequest;
import com.codex.ecom.order.dto.OrderRequest;
import com.codex.ecom.order.exception.OrderServiceException;
import com.codex.ecom.order.model.Order;
import com.codex.ecom.order.model.OrderItems;
import com.codex.ecom.order.repository.OrderRepository;
import com.codex.ecom.order.response.Inventory;
import com.codex.ecom.order.response.Product;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ProductService productService;
	@Autowired
	private InventoryService inventoryService;
	private static Date CURRENT_DATETIME = new Date(System.currentTimeMillis());

	public List<Order> allOrders() {
		return orderRepository.findAll();
	}

	public Order getOrderById(String orderId) {
		return orderRepository.findById(orderId).orElseThrow(
				() -> new OrderServiceException(String.format("Order for the id %d is not available", orderId)));
	}

	public Order createOrder(OrderRequest orderRequest) {
		Order order = Order.builder().createdDate(CURRENT_DATETIME).updatedDate(CURRENT_DATETIME)
				.orderItems(orderRequest.getOrderItems()).invoiceAmt(getInvoiceAmount(orderRequest.getOrderItems()))
				.build();
		updateInventory(orderRequest);
		return orderRepository.insert(order);
	}

	private void updateInventory(OrderRequest orderRequest) {
		orderRequest.getOrderItems().forEach((order) -> {
			InventoryRequest inventoryRequest = InventoryRequest.builder().productId(order.getProductId())
					.units(order.getQuantity()).build();
			ResponseEntity<Inventory> inventoryResonse = inventoryService.deductStockUnits(inventoryRequest);
			if (inventoryResonse.getStatusCode() != HttpStatus.OK) {
				throw new OrderServiceException("Unable to update inventory");
			} else {
				log.info("Successfuly updated inventory for product Id: {}", order.getProductId());
			}
		});
	}

	public void update(Order order) {
		orderRepository.save(order);
	}

	public void delete(String orderId) {
		orderRepository.deleteById(orderId);
	}

	public void validateRequest(OrderRequest orderRequest) {
		orderRequest.getOrderItems().forEach((order) -> {
			if (order.getQuantity() < 0) {
				throw new OrderServiceException("Invalid quantity");
			}
			ResponseEntity<Product> productResponse = productService.getProductResponse(order);
			if (productResponse.getStatusCode() == HttpStatus.OK) {
				if (!order.getProductId().equals(productResponse.getBody().getProductId())) {
					throw new OrderServiceException("Invalid product Id!");
				}
			} else {
				throw new OrderServiceException("Unable to fetch product Id!");
			}
			ResponseEntity<Inventory> inventoryResonse = inventoryService.getProductUnits(order.getProductId());
			if (inventoryResonse.getStatusCode() == HttpStatus.OK) {
				if (order.getQuantity() > inventoryResonse.getBody().getUnits()) {
					throw new OrderServiceException(String.format("Availble stock for product Id=%s is less than ",
							order.getProductId(), order.getQuantity()));
				}
			} else {
				throw new OrderServiceException("Unable to fetch stocks from inventory");
			}
		});
	}

	public BigDecimal getInvoiceAmount(List<OrderItems> orderItems) {
		List<BigDecimal> listOfItemCosts = new ArrayList<>();
		orderItems.forEach((order) -> {
			BigDecimal itemCost = BigDecimal.ZERO;
			ResponseEntity<Product> productResponse = productService.getProductResponse(order);
			if (productResponse.getStatusCode() == HttpStatus.OK) {
				itemCost = productResponse.getBody().getPrice().multiply(BigDecimal.valueOf(order.getQuantity()));
				listOfItemCosts.add(itemCost);
			}
		});
		return getTotalAmount(listOfItemCosts);
	}

	private BigDecimal getTotalAmount(List<BigDecimal> listOfItemCosts) {
		BigDecimal sum = BigDecimal.ZERO;
		for (BigDecimal amt : listOfItemCosts) {
			sum = sum.add(amt);
		}
		return sum;
	}
}
