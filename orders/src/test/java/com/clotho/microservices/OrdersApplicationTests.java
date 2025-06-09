package com.clotho.microservices;

import com.clotho.microservices.dto.OrderRequest;
import com.clotho.microservices.model.Order;
import com.clotho.microservices.repository.OrderRepository;
import com.clotho.microservices.service.OrderService;
import com.clotho.microservices.stubs.InventoryClientStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureWireMock(port = 0)
class OrderServiceIntegrationTest {

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderRepository orderRepository;

	@BeforeEach
	void setUp() {
		orderRepository.deleteAll();
	}

	@Test
	void testPlaceAndGetOrder() {
		OrderRequest.UserDetails userDetails = new OrderRequest.UserDetails(
				"test@example.com",
				"Test",
				"Customer"
		);

		OrderRequest request = new OrderRequest(
				"684022241d6dc037367e156e",
				2,
				"test-customer-1",
				userDetails,
				"123 Main St",
				new BigDecimal("99.99")
		);
		InventoryClientStub.stubInventoryCall("684022241d6dc037367e156e", 2);
		orderService.placeOrder(request);

		Order savedOrder = orderRepository.findAll().get(0);
		assertThat(savedOrder.getProductId()).isEqualTo("684022241d6dc037367e156e");
		assertThat(savedOrder.getProductId()).isEqualTo("684022241d6dc037367e156e");

		Order fetchedOrder = orderService.getOrderById(savedOrder.getOrderId());
		assertThat(fetchedOrder).isNotNull();
		assertThat(fetchedOrder.getOrderId()).isEqualTo(savedOrder.getOrderId());
		assertThat(fetchedOrder.getAmount()).isEqualTo(new BigDecimal("99.99"));
	}
}
