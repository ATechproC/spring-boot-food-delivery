package com.atechproc.controller;

import com.atechproc.dto.OrderDto;
import com.atechproc.enums.ORDER_STATUS;
import com.atechproc.request.order.CreateOrderReq;
import com.atechproc.response.ApiResponse;
import com.atechproc.service.order.IOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createOrderHandler(
            @Valid
            @RequestBody CreateOrderReq req,
            @RequestParam Long resId,
            @RequestHeader("Authorization") String jwt
            ) {
        OrderDto order = orderService.createOrder(req, resId, jwt);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("Order creates successfully", order));
    }

    @PutMapping("/update-status/{orderId}")
    public ResponseEntity<ApiResponse> updateOrderStatusHandler(
            @RequestParam ORDER_STATUS status,
            @PathVariable Long orderId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        OrderDto order = orderService.updateOrderStatus(status, orderId, jwt);
        return ResponseEntity.ok(new ApiResponse("Status updated successfully", order));
    }

    @DeleteMapping("/cancel/{orderId}")
    public ResponseEntity<ApiResponse> cancelOrderHandler(
            @PathVariable Long orderId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        orderService.cancelOrder(orderId, jwt);
        return ResponseEntity.ok(new ApiResponse("Order canceled successfully", null));
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse> getUserOrders(@RequestHeader("Authorization") String jwt){
        List<OrderDto> orders = orderService.getUserOrders(jwt);
        return ResponseEntity.ok(new ApiResponse("Success", orders));
    }

    @GetMapping("/restaurant")
    public ResponseEntity<ApiResponse> getRestaurantOrders(@RequestHeader("Authorization") String jwt){
        List<OrderDto> orders = orderService.getRestaurantOrders(jwt);
        return ResponseEntity.ok(new ApiResponse("Success", orders));
    }

}
