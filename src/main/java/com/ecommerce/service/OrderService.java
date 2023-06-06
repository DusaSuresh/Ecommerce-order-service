package com.ecommerce.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.messaging.OrderEventProducer;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderPlacedEvent;
import com.ecommerce.model.OrderResponseVO;
import com.ecommerce.repository.OrderRepository;

import jakarta.validation.Valid;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	OrderResponseVO orderResponseVO;
	
	@Autowired
	OrderEventProducer orderEventProducer;

	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

	public ResponseEntity<OrderResponseVO> placeOrder(@Valid Order order) {

		logger.info("****** Start of Request in Order Service ****** Method is PlaceOrder");

		Order placedOrder = orderRepository.save(order);

		if (placedOrder.getOrderId() != null) {
			logger.info("****** Order Placed Successfully ****** OrderId #" + placedOrder.getOrderId());
			orderResponseVO = OrderResponseVO.builder()
					.message("Order Placed Successfully").timeStamp(System.currentTimeMillis()).order(placedOrder)
					.build();
			logger.info("****** End of Request in Order Service ****** Success Scenario");
			
			logger.info("Order placed kafka producer event calling.....");
			
			// Publish "order placed" event to Kafka
	        OrderPlacedEvent event = new OrderPlacedEvent(order.getOrderId(), order.getCustomerName(), order.getProductDetails());
	        orderEventProducer.publishOrderPlacedEvent(event);
	        
			return new ResponseEntity<>(orderResponseVO, HttpStatus.CREATED);

		} else {
			logger.info("****** Something broken while placing the order, check of exception");
			orderResponseVO = OrderResponseVO.builder()
					.message("Something broken while placing the order").timeStamp(System.currentTimeMillis())
					.order(placedOrder).build();
			logger.info("****** End of Request in Order Service ****** Failure Scenario");
			return new ResponseEntity<>(orderResponseVO, HttpStatus.EXPECTATION_FAILED);

		}
		
	}

	public ResponseEntity<Order> getOrder(Long orderId) {
		// Retrieve the order from the repository
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        // Check if the order exists
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            // Return the order in the response
            return ResponseEntity.ok(order);
        } else {
            // Return a not found response
            return ResponseEntity.notFound().build();
        }
	}

	public ResponseEntity<String> updateOrder(Long orderId, @Valid Order updatedOrder) {
		// Retrieve the order from the repository
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        // Check if the order exists
        if (optionalOrder.isPresent()) {
            Order existingOrder = optionalOrder.get();

            // Update the order
            existingOrder.setCustomerName(updatedOrder.getCustomerName());
            existingOrder.setNoOfItems(updatedOrder.getNoOfItems());

            // Save the updated order using the repository
            orderRepository.save(existingOrder);

            // Return success response
            return ResponseEntity.ok("Order updated successfully");
        } else {
            // Return a not found response
            return ResponseEntity.notFound().build();
        }

	}

	public ResponseEntity<String> updateOrder(Long orderId) {
		// Retrieve the order from the repository
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        // Check if the order exists
        if (optionalOrder.isPresent()) {
            // Delete the order using the repository
            orderRepository.delete(optionalOrder.get());

            // Return success response
            return ResponseEntity.ok("Order deleted successfully");
        } else {
            // Return a not found response
            return ResponseEntity.notFound().build();
        }
	}

}
