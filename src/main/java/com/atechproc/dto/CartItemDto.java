package com.atechproc.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartItemDto {
    private Long id;
    private FoodDto food;
    private Integer quantity;
    private BigDecimal totalPrice;
}
