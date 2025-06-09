package com.clotho.microservices;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;

import static org.hamcrest.Matchers.notNullValue;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductApplicationTests {

	@ServiceConnection
	static MongoDBContainer mongoDbContainer = new MongoDBContainer("mongo:7.0.5");

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost:"+port;

	}

	static {
		mongoDbContainer.start();
	}

	@Test
	void shouldCreateProduct() {
		String requestBody = """
				{
				   "name": "Sample Product",
				   "description": "A sample product for testing.",
				   "price": 19.99,
				   "category": "Electronics",
				   "imageUrl": "https://example.com/image.jpg"
				 }
				""";
		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/products")
				.then()
				.statusCode(201)
				.body("id", Matchers.notNullValue()).body("name", Matchers.equalTo("Sample Product"))
				.body("description", Matchers.equalTo("A sample product for testing."))
				.body("price", Matchers.equalTo(19.99F));
	}


}
