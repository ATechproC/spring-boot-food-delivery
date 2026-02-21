package com.atechproc.request.order;

import com.atechproc.model.UserAddress;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateOrderReq {
    @NotBlank(message = "payment method is required")
    private String payment;

    @NotNull(message = "delivery address is required")
    private UserAddressReq deliveryAddress;
}
