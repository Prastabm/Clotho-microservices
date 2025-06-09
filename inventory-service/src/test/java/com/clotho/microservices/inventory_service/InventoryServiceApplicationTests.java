package com.clotho.microservices.inventory_service;



import com.clotho.microservices.inventory_service.model.Inventory;
import com.clotho.microservices.inventory_service.repository.InventoryRepository;
import com.clotho.microservices.inventory_service.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class InventoryServiceIntegrationTest {

	@Autowired
	private InventoryService inventoryService;

	@Autowired
	private InventoryRepository inventoryRepository;

	@BeforeEach
	void setUp() {
		inventoryRepository.deleteAll();
		inventoryRepository.save(new Inventory(null, "prod-1", 10));
		inventoryRepository.save(new Inventory(null, "prod-2", 0));
	}

	@Test
	void isAvailable_shouldReturnTrue_whenSufficientQuantityExists() {
		boolean available = inventoryService.isAvailable("prod-1", 5);
		assertThat(available).isTrue();
	}

	@Test
	void isAvailable_shouldReturnFalse_whenInsufficientQuantity() {
		boolean available = inventoryService.isAvailable("prod-1", 20);
		assertThat(available).isFalse();
	}

	@Test
	void isAvailable_shouldReturnFalse_whenProductDoesNotExist() {
		boolean available = inventoryService.isAvailable("prod-unknown", 1);
		assertThat(available).isFalse();
	}

	@Test
	void isAvailable_shouldReturnFalse_whenQuantityIsZero() {
		boolean available = inventoryService.isAvailable("prod-2", 1);
		assertThat(available).isFalse();
	}
}