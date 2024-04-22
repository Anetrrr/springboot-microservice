package com.ecobank.orderservice.service;


import com.ecobank.orderservice.dto.InventoryResponse;
import com.ecobank.orderservice.dto.OrderLineItemsDto;
import com.ecobank.orderservice.dto.OrderRequest;
import com.ecobank.orderservice.dto.OrderResponse;
import com.ecobank.orderservice.model.Order;
import com.ecobank.orderservice.model.OrderLineItems.OrderLineItems;
import com.ecobank.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Arrays;
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

   private final WebClient.Builder webClientBuilder;
    public OrderResponse placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = Collections.emptyList();

        if (orderRequest.getOrderLineItemsDtoList() != null) {
            orderLineItems = orderRequest.getOrderLineItemsDtoList()
                    .stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());

            // Moved log statement here
            log.info("items::: "+orderLineItems);
            log.info("request:::{}",orderRequest );
        } else {
            log.warn("OrderLineItemsDtoList is null for order request: {}", orderRequest);
        }
        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();
        log.info("skucode list:::{}", skuCodes);

        //Call inventory service. place order if product is in stock.8079

        try {
            InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
                    .uri("http://inventoryservice/api/inventory/get",
                            uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();

            assert inventoryResponseArray != null;
            boolean allProductsInStock =  Arrays.stream(inventoryResponseArray)
                    .allMatch(InventoryResponse::isInStock);

            if (allProductsInStock) {
                orderRepository.save(order);
                OrderResponse response = new OrderResponse();
                response.setResponse("Order Placed Successfully");
                return  response;
            } else {
                throw new IllegalArgumentException("Product is not in stock, please try again later");
            }
        } catch (WebClientResponseException.NotFound e) {
            throw new IllegalArgumentException("Inventory service not found or endpoint is incorrect");
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching inventory", e);
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
