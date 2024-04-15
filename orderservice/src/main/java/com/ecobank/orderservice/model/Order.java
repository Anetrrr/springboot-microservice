package com.ecobank.orderservice.model;

import com.ecobank.orderservice.model.OrderLineItems.OrderLineItems;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table( name= "t-orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String orderNumber;

    @OneToMany(cascade= CascadeType.ALL)
    private List<OrderLineItems> orderLineItemsList;

}
