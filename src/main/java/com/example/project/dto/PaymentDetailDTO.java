package com.example.project.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetailDTO {
	long totalPrice;
	Place dpAirport;
	String orderTime;
    String orderDate;
    String dpDate;
    List<CartItemDTO> itemList;
}
