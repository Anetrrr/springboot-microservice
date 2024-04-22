package com.ecobank.orderservice.controller;

import com.ecobank.orderservice.dto.OrderRequest;
import com.ecobank.orderservice.dto.OrderResponse;
import com.ecobank.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse placeOrder(@RequestBody OrderRequest orderRequest){
         return orderService.placeOrder(orderRequest);
    }


}
