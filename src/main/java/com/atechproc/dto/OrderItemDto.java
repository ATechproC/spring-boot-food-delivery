package com.atechproc.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private Long id;
    private int quantity;
    private BigDecimal totalPrice;
}
