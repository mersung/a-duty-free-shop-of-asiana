package com.example.project.dto.order;

import javax.validation.constraints.NotEmpty;

import org.apache.ibatis.type.Alias;

import com.example.project.dto.Place;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("OrderDTO")
public class OrderDTO { // 주문 수량 필요
	int id;
	OrderStatus orderStatus = OrderStatus.PROGRESS;
	Place dpAirport; //
    Place arAirport; //
    long totalPrice;
    @NotEmpty(message = "탑승일을 입력해주세요.")
    String dpDate;
    String orderTime;
    String orderDate;
    String tid;
    int cnt; //
    int userId;
	int productId; //
}
