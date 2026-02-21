package com.atechproc.dto;

import com.atechproc.enums.ORDER_STATUS;
import com.atechproc.model.UserAddress;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private ORDER_STATUS orderStatus;
    private UserAddressDto deliveryAddress;
    private List<OrderItemDto> items;
    private String payment;
    private int totalItem;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
}
