package com.ecobank.orderservice.service;

import com.ecobank.orderservice.dto.OrderLineItemsDto;
import com.ecobank.orderservice.dto.OrderRequest;
import com.ecobank.orderservice.model.Order;
import com.ecobank.orderservice.model.OrderLineItems.OrderLineItems;
import com.ecobank.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = Collections.emptyList();

        if (orderRequest.getOrderLineItemsDtoList() != null) {
            orderLineItems = orderRequest.getOrderLineItemsDtoList()
                    .stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());

            // Moved log statement here
            log.info(orderLineItems.toString());
        } else {
            log.warn("OrderLineItemsDtoList is null for order request: {}", orderRequest);
        }
    }
        private OrderLineItems mapToDto (OrderLineItemsDto orderLineItemsDto){

            OrderLineItems orderLineItems = new OrderLineItems();
            orderLineItems.setPrice(orderLineItemsDto.getPrice());
            orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
            orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
            return orderLineItems;


        }

    }
