package com.atechproc.dto;

import com.atechproc.model.CartItem;
import com.atechproc.model.User;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartDto {
    private Long id;
    private List<CartItemDto> items;
    private BigDecimal total;
}
