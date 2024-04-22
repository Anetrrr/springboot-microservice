package com.ecobank.inventoryservice;

import com.ecobank.inventoryservice.model.Inventory;
import com.ecobank.inventoryservice.repository.InventoryRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryserviceApplication.class, args);
	}


	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository){

		return args -> {
			Inventory inventory = new Inventory();
			inventory.setSkuCode("Toyota Corolla");
			inventory.setQuantity(2);

			Inventory inventory1 = new Inventory();
			inventory1.setSkuCode("Tesla Model S");
			inventory1.setQuantity(1);

			Inventory inventory2 = new Inventory();
			inventory2.setSkuCode("Lexus RX350");
			inventory2.setQuantity(44);

			inventoryRepository.save(inventory1);
			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory2);
		};



	}
}
