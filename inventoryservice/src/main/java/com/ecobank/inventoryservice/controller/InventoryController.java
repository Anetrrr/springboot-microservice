package com.ecobank.inventoryservice.controller;

import com.ecobank.inventoryservice.dto.InventoryResponse;
import com.ecobank.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;
//http://localhost:8081/api/inventory/lamborghini,ferrari
    //its better we go with this format below
//http://localhost:8081/api/inventory?sku-code=lamborghini&sku-code=ferrari
//    @GetMapping("/{sku-code}")
//    @ResponseStatus(HttpStatus.OK)
//    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode){
//            return inventoryService.isInStock(skuCode);
//    }


        @GetMapping("/get")
        @ResponseStatus(HttpStatus.OK)
        public List<InventoryResponse> isInStock(@RequestParam(name = "skuCode") List<String> skuCode){
            return inventoryService.isInStock(skuCode);
        }
    }


