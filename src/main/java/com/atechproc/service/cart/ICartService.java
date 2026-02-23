package com.atechproc.service.cart;

import com.atechproc.dto.CartDto;
import com.atechproc.dto.CartItemDto;
import com.atechproc.model.Cart;
import com.atechproc.model.CartItem;
import com.atechproc.request.cart.AddToCartRequest;
import org.apache.coyote.BadRequestException;

import java.math.BigDecimal;

public interface ICartService {
    CartItemDto addItemToCart(Long foodId, String jwt, AddToCartRequest request) throws BadRequestException;
    CartItemDto updateCartItemQuantity(Long itemId, int quantity, String jwt) throws BadRequestException;
    CartItem getCartItemById(Long itemId);
    CartDto removeItemFromCart(Long itemId, String jwt) throws BadRequestException;
    BigDecimal calculateCartTotalPrice(String jwt);
    Cart getUserCart(String jwt);
    CartDto clearCart(String jwt);
}
