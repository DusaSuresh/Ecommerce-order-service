package com.ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name="order")
public class Order {
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long orderId;
	
	private String customerName;
	
	private String productDetails;
	
	private int noOfItems;
	
	private Long discount;
	
	private Long deliveryCharges;

	
}
