package com.codex.ecom.order.controller;

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

import com.codex.ecom.order.dto.OrderRequest;
import com.codex.ecom.order.dto.OrderResponse;
import com.codex.ecom.order.model.Order;
import com.codex.ecom.order.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderService orderService;

	@GetMapping("/all")
	public ResponseEntity<List<Order>> getAllOrders(@RequestParam String OrderId) {
		return new ResponseEntity<>(orderService.allOrders(), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<Order> getOrder(@RequestParam String orderId) {
		return new ResponseEntity<>(orderService.getOrderById(orderId), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<OrderResponse> createOrder(@NotNull @RequestBody OrderRequest orderRequest) {
		orderService.validateRequest(orderRequest);
		String orderId = orderService.createOrder(orderRequest).getOrderId();
		return new ResponseEntity<>(new OrderResponse(String.format("Order id=%s has been created", orderId)),
				HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<OrderResponse> updateOrder(@NotNull @RequestBody Order order) {
		orderService.update(order);
		return new ResponseEntity<OrderResponse>(
				new OrderResponse(String.format("Order id=%s has been created", order.getOrderId())), HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<OrderResponse> deleteOrder(@RequestParam String orderId) {
		orderService.delete(orderId);
		return new ResponseEntity<OrderResponse>(
				new OrderResponse(String.format("Order id=%s has been created", orderId)), HttpStatus.OK);
	}
}
