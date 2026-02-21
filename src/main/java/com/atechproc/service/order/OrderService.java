package com.atechproc.service.order;

import com.atechproc.dto.OrderDto;
import com.atechproc.enums.ORDER_STATUS;
import com.atechproc.exception.ResourceNotFoundException;
import com.atechproc.mapper.OrderMapper;
import com.atechproc.model.*;
import com.atechproc.repository.*;
import com.atechproc.request.order.CreateOrderReq;
import com.atechproc.service.res.IResService;
import com.atechproc.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserAddressRepository addressRepository;
    private final IResService resService;
    private final OrderItemRepository itemRepository;
    private final ResRepository restaurantRepository;

    @Override
    public OrderDto createOrder(CreateOrderReq req, Long resId, String jwt) {

        User user = userService.getUserProfile(jwt);

        Restaurant res = restaurantRepository.findById(resId).orElseThrow(() -> new ResourceNotFoundException("Res not found"));

        UserAddress address = addressRepository.findByStreetAddressAndCountryAndPostalCodeAndStateProvinceAndCostumer(
                req.getDeliveryAddress().getStreetAddress(),
                req.getDeliveryAddress().getCountry(),
                req.getDeliveryAddress().getPostalCode(),
                req.getDeliveryAddress().getStateProvince(),
                user
        ).orElseGet(() -> {

            UserAddress adder = new UserAddress();
            adder.setCostumer(user);
            adder.setStreetAddress(req.getDeliveryAddress().getStreetAddress());
            adder.setCountry(req.getDeliveryAddress().getCountry());
            adder.setPostalCode(req.getDeliveryAddress().getPostalCode());
            adder.setStateProvince(req.getDeliveryAddress().getStateProvince());

            return addressRepository.save(adder);
        });

        Order order = new Order();
        order.setDeliveryAddress(address);
        order.setCustomer(user);
        order.setRestaurant(res);
        order.setPayment(req.getPayment());
        order.setTotalPrice(user.getCart().getTotal());
        order.setTotalItem(user.getCart().getItems().size());
        order.setRestaurant(res);

        Order savedOrder = orderRepository.save(order);

        List<OrderItem> items = new ArrayList<>();

        for(CartItem cartItem : user.getCart().getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setFood(cartItem.getFood());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());
            savedOrder.getItems().add(orderItem);
        }

        return OrderMapper.toOrderDto(orderRepository.save(savedOrder));
    }

    @Override
    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    public OrderDto updateOrderStatus(ORDER_STATUS status, Long orderId, String jwt)
            throws Exception {

        Order order = getOrderById(orderId);
        User user = userService.getUserProfile(jwt);

        Restaurant res = resService.getResByUserId(user.getId());

        if(!res.getOrders().contains(order)) {
            throw new Exception("Your are not allowed to update this order's status");
        }

        order.setOrderStatus(status);

        Order savedOrder = orderRepository.save(order);

        return OrderMapper.toOrderDto(savedOrder);
    }

    @Override
    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    public void cancelOrder(Long orderId, String jwt) throws Exception {
        Order order = getOrderById(orderId);
        User user = userService.getUserProfile(jwt);
        Restaurant res = resService.getResByUserId(user.getId());

        if(!user.getOrders().contains(order)) {
            throw new Exception("Your are not allowed to cancel this order.");
        }

        order.setOrderStatus(ORDER_STATUS.CANCELLED);
        orderRepository.save(order);
    }

    @Override
    public List<OrderDto> getUserOrders(String jwt) {
        User user = userService.getUserProfile(jwt);
        List<Order> orders = orderRepository.findByCustomer_id(user.getId());
        return OrderMapper.toOrderDTOs(orders);
    }

    @Override
    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    public List<OrderDto> getRestaurantOrders(String jwt) {
        User user = userService.getUserProfile(jwt);
        List<Order> orders = orderRepository.findByCustomer_id(user.getId());
        return OrderMapper.toOrderDTOs(orders);
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + orderId));
    }
}
