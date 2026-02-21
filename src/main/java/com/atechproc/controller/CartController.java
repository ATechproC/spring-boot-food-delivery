package com.atechproc.controller;

import com.atechproc.dto.CartDto;
import com.atechproc.dto.CartItemDto;
import com.atechproc.mapper.CartMapper;
import com.atechproc.model.Cart;
import com.atechproc.model.CartItem;
import com.atechproc.response.ApiResponse;
import com.atechproc.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("${api.prefix}/carts")
@RequiredArgsConstructor
public class CartController {

    private final ICartService cartService;

    @PostMapping("/add-item")
    public ResponseEntity<ApiResponse> addItemToCart(
            @RequestParam int quantity,
            @RequestParam Long foodId,
            @RequestHeader("Authorization") String jwt
    ) throws BadRequestException {
        CartItemDto cartItem = cartService.addItemToCart(quantity, foodId, jwt);
        return ResponseEntity.ok(new ApiResponse("item added to cart successfully", cartItem));
    }

    @PutMapping("/update-quantity/item/{itemId}")
    public ResponseEntity<ApiResponse> updateCartQuantityHandler(
            @PathVariable Long itemId,
            @RequestParam int quantity,
            @RequestHeader("Authorization") String jwt
    ) throws BadRequestException {
        CartItemDto cartItem = cartService.updateCartItemQuantity(itemId, quantity, jwt);
        return ResponseEntity.ok(new ApiResponse("quantity updated successfully", cartItem));
    }

    @GetMapping("/cart-item/{itemId}")
    public ResponseEntity<ApiResponse> getCartItemHandler(
            @PathVariable Long itemId
    ) throws BadRequestException {
        CartItem cartItem = cartService.getCartItemById(itemId);
        return ResponseEntity.ok(new ApiResponse("Success", CartMapper.toCartItemDto(cartItem)));
    }

    @PutMapping("/remove-item/{itemId}")
    public ResponseEntity<ApiResponse> removeItemFromCartHandler(
            @PathVariable Long itemId,
            @RequestHeader("Authorization") String jwt
    ) throws BadRequestException {
        CartDto cart = cartService.removeItemFromCart(itemId, jwt);
        return ResponseEntity.ok(new ApiResponse("cart item removed successfully", cart));
    }

    @GetMapping("/total-price")
    public ResponseEntity<ApiResponse> calculateCartTotalPriceHandler(
            @RequestHeader("Authorization") String jwt
    ) throws BadRequestException {
        BigDecimal total = cartService.calculateCartTotalPrice(jwt);
        return ResponseEntity.ok(new ApiResponse("Success", total));
    }

    @GetMapping("/user-cart")
    public ResponseEntity<ApiResponse> getUserCartHandler(
            @RequestHeader("Authorization") String jwt
    ) throws BadRequestException {
        Cart cart = cartService.getUserCart(jwt);
        return ResponseEntity.ok(new ApiResponse("Success", CartMapper.toCartDto(cart)));
    }

    @PostMapping("/clear")
    public ResponseEntity<ApiResponse> clearCart(
            @RequestHeader("Authorization") String jwt
    ) {
        CartDto cart = cartService.clearCart(jwt);
        return ResponseEntity.ok(new ApiResponse("Cart was cleared successfully", cart));
    }
}
