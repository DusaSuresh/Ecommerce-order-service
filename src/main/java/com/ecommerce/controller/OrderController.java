package com.ecommerce.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.model.Order;
import com.ecommerce.model.OrderResponseVO;
import com.ecommerce.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/order")
public class OrderController {

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	OrderService orderService;

	@PostMapping
	@RequestMapping("/placeOrder")
	public ResponseEntity<OrderResponseVO> placeOrder(@Valid @RequestBody Order order) {
		
		logger.info("****** Request in Order controller ****** Method is PlaceOrder");
		return orderService.placeOrder(order);
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<Order> getOrder(@PathVariable Long orderId) {
		
		logger.info("****** Request in Order controller ****** Method is getOrder");
		return orderService.getOrder(orderId);
	}

	@PutMapping("/{orderId}")
	public ResponseEntity<String> updateOrder(@PathVariable Long orderId, @Valid @RequestBody Order updatedOrder) {

		logger.info("****** Request in Order controller ****** Method is update Order");
		return orderService.updateOrder(orderId, updatedOrder);
	}

	@DeleteMapping("/{orderId}")
	public ResponseEntity<String> deleteOrder(@PathVariable Long orderId) {

		logger.info("****** Request in Order controller ****** Method is delete Order");
		return orderService.updateOrder(orderId);

	}

}
