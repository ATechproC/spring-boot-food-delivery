package com.atechproc.service.cart;

import com.atechproc.dto.CartDto;
import com.atechproc.dto.CartItemDto;
import com.atechproc.exception.ResourceNotFoundException;
import com.atechproc.mapper.CartMapper;
import com.atechproc.model.Cart;
import com.atechproc.model.CartItem;
import com.atechproc.model.Food;
import com.atechproc.model.User;
import com.atechproc.repository.CartItemRepository;
import com.atechproc.repository.CartRepository;
import com.atechproc.request.cart.AddToCartRequest;
import com.atechproc.service.food.FoodService;
import com.atechproc.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final FoodService foodService;
    private final UserService userService;

    @Override
    public CartItemDto addItemToCart(Long foodId, String jwt, AddToCartRequest request)
            throws BadRequestException {

        User user = userService.getUserProfile(jwt);

        Food food = foodService.getFoodById(foodId);

        Cart cart = user.getCart();

        for(CartItem cartItem : cart.getItems()) {
            if(cartItem.getFood().getId().equals(foodId)) {
//                updateCartItemQuantity(cartItem.getId(), quantity, jwt);
                cartItem.setIngredientsItems(request.getIngredientsItems());
                CartItem savedCartItem = cartItemRepository.save(cartItem);
                return CartMapper.toCartItemDto(savedCartItem);
            }
        }

        CartItem cartItem = new CartItem();
        cartItem.setCart(user.getCart());
        cartItem.setFood(food);
//        cartItem.setQuantity(quantity);
        cartItem.calculateTotalPrice();
        System.out.println("##############################");
        cartItem.setIngredientsItems(request.getIngredientsItems());

        CartItem savedItem = cartItemRepository.save(cartItem);

        cart.getItems().add(savedItem);
        cart.calculateCartTotalPrice();
        cartRepository.save(cart);

        return CartMapper.toCartItemDto(savedItem);
    }

    @Override
    public CartItemDto updateCartItemQuantity(Long itemId, int quantity, String jwt)
            throws BadRequestException {
        CartItem cartItem = getCartItemById(itemId);
        User user = userService.getUserProfile(jwt);

        if(quantity < 0) {
            quantity = 0;
        }

        if(!user.getCart().getItems().contains(cartItem)) {
            throw new BadRequestException("Your not allowed to update this cartItem quantity.");
        }

        cartItem.setQuantity(quantity);
        cartItem.calculateTotalPrice();
        user.getCart().calculateCartTotalPrice();
        cartRepository.save(user.getCart());

        CartItem savedItem = cartItemRepository.save(cartItem);

        return CartMapper.toCartItemDto(savedItem);
    }

    @Override
    public CartItem getCartItemById(Long itemId) {
        return cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id " + itemId));
    }

    @Override
    public CartDto removeItemFromCart(Long itemId, String jwt)
            throws BadRequestException {

        CartItem item = getCartItemById(itemId);
        User user = userService.getUserProfile(jwt);
        Cart cart = user.getCart();

        if(!cart.getItems().contains(item)) {
            throw new BadRequestException("You are not allowed to remove this cart item.");
        }

        cart.getItems().remove(item);
        item.setTotalPrice(BigDecimal.ZERO);

        Cart savedCart = cartRepository.save(cart);

        return CartMapper.toCartDto(savedCart);
    }

    @Override
    public BigDecimal calculateCartTotalPrice(String jwt) {
        Cart cart = getUserCart(jwt);
        return cart.calculateCartTotalPrice();
    }

    @Override
    public Cart getUserCart(String jwt) {
        return userService.getUserProfile(jwt).getCart();
    }

    @Override
    public CartDto clearCart(String jwt) {

        Cart cart = getUserCart(jwt);
        cart.getItems().clear();
        cart.setTotal(BigDecimal.ZERO);

        Cart savedCart = cartRepository.save(cart);

        return CartMapper.toCartDto(savedCart);
    }
}
