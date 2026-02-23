package com.atechproc.mapper;

import com.atechproc.dto.OrderDto;
import com.atechproc.dto.OrderItemDto;
import com.atechproc.dto.UserAddressDto;
import com.atechproc.model.Order;
import com.atechproc.model.OrderItem;
import com.atechproc.model.UserAddress;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {
    public static OrderDto toOrderDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setOrderStatus(order.getOrderStatus());
        dto.setItems(toOrderItemDTOs(order.getItems()));
        dto.setTotalPrice(order.getTotalPrice());
        dto.setTotalItem(order.getTotalItem());
        dto.setPayment(order.getPayment());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setDeliveryAddress(toUserAddressDto(order.getDeliveryAddress()));
        dto.setUsername(order.getCustomer().getUsername());

        return dto;
    }

    public static List<OrderDto> toOrderDTOs(List<Order> orders) {
        return orders.stream()
                .map(OrderMapper::toOrderDto).collect(Collectors.toList());
    }

    public static OrderItemDto toOrderItemDto(OrderItem orderItem) {
        OrderItemDto dto = new OrderItemDto();
        dto.setId(orderItem.getId());
        dto.setQuantity(orderItem.getQuantity());
        dto.setTotalPrice(orderItem.getTotalPrice());

        return dto;
    }

    public static List<OrderItemDto> toOrderItemDTOs(List<OrderItem> items) {
        return items.stream()
                .map(OrderMapper::toOrderItemDto).collect(Collectors.toList());
    }

    public static UserAddressDto toUserAddressDto(UserAddress address) {
        UserAddressDto dto = new UserAddressDto();

        dto.setId(address.getId());
        dto.setCountry(address.getCountry());
        dto.setStreetAddress(address.getStreetAddress());
        dto.setStateProvince(address.getStateProvince());
        dto.setPostalCode(address.getPostalCode());

        return dto;
    }
 }
