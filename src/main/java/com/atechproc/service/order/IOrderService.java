package com.atechproc.service.order;

import com.atechproc.dto.OrderDto;
import com.atechproc.enums.ORDER_STATUS;
import com.atechproc.model.Order;
import com.atechproc.model.OrderItem;
import com.atechproc.request.order.CreateOrderReq;

import java.util.List;

public interface IOrderService {
    OrderDto createOrder(CreateOrderReq req, Long resId, String jwt);
    OrderDto updateOrderStatus(ORDER_STATUS status, Long orderId, String jwt) throws Exception;
    void cancelOrder(Long orderId, String jwt) throws Exception;
    List<OrderDto> getUserOrders(String jwt);
    List<OrderDto> getRestaurantOrders(String jwt, ORDER_STATUS status);
    Order getOrderById(Long orderId);
}
