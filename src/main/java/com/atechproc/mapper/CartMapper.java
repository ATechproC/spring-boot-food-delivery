package com.atechproc.mapper;

import com.atechproc.dto.CartDto;
import com.atechproc.dto.CartItemDto;
import com.atechproc.model.Cart;
import com.atechproc.model.CartItem;

import java.util.List;
import java.util.stream.Stream;

public class CartMapper {
    public static CartItemDto toCartItemDto(CartItem item) {
        CartItemDto dto = new CartItemDto();

        dto.setId(item.getId());
        dto.setFood(FoodMapper.toDto(item.getFood()));
        dto.setQuantity(item.getQuantity());
        dto.setTotalPrice(item.getTotalPrice());

        return dto;
    }

    public static List<CartItemDto> toCartItemDTOs(List<CartItem> items) {
        return items.stream()
                .map(CartMapper::toCartItemDto).toList();
    }

    public static CartDto toCartDto(Cart cart) {
        CartDto dto = new CartDto();

        dto.setId(cart.getId());
        dto.setItems(toCartItemDTOs(cart.getItems()));
        dto.setTotal(cart.getTotal());

        return dto;
    }

    public static List<CartDto> toCartDTOs(List<Cart> carts) {
        return carts.stream()
                .map(CartMapper::toCartDto).toList();
    }
}
