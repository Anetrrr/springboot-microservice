package com.ecobank.inventoryservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class InventoryRequest {
    private List<String> skuCode;
    private String oneCode;
}
