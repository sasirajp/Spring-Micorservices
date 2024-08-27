package com.project.micoserivce.order;

import io.restassured.RestAssured;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;

import static org.hamcrest.MatcherAssert.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderServiceApplicationTests {

	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0");
	@LocalServerPort
	private Integer port;

	static {
		mySQLContainer.start();
	}

	@BeforeEach
	void setUp() {
		RestAssured.baseURI = "http://localhost/";
		RestAssured.port = port;
	}

	@Test
	void shouldCreateOrder() {
		String requestBody = """
				{
				    "skuCode": "iPhone 15",
				    "price": 1000,
				    "quantity": 101
				}""";

		var responseString = RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/order")
				.then()
				.statusCode(201)
				.extract()
				.body().asString();

		assertThat(responseString, Matchers.is("Order placed successfully"));
	}

}
