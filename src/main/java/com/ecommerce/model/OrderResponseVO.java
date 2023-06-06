package com.ecommerce.model;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.Data;

@Component
@Data
@Builder
public class OrderResponseVO {
	
	private Long timeStamp;
	private String message;
	private List<String> errors;
	private Order order;

}
