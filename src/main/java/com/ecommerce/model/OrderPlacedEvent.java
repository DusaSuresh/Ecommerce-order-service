package com.ecommerce.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderPlacedEvent {
    private Long orderId;
    private String customerName;
    private String productDetails;
}
