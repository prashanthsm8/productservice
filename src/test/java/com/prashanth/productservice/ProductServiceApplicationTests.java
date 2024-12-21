package com.prashanth.productservice;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.assertions.Assertions;
import com.prashanth.productservice.dto.ProductRequest;
import com.prashanth.productservice.repository.ProductRepository;

import ch.qos.logback.core.status.Status;


@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {
	
	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.4.2"));
	
	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dPR)
	{
		dPR.add("spring.data.mongodb.uri",mongoDBContainer::getReplicaSetUrl);
	}
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private ProductRepository productRepo;

	@Test
	void shouldCreateProduct() throws Exception {
		
		ProductRequest pr = getProductRequest();
		String prodReqStr = objectMapper.writeValueAsString(pr);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product").
				contentType(MediaType.APPLICATION_JSON).content(prodReqStr))
		.andExpect(MockMvcResultMatchers.status().isCreated());
		
		System.out.println(productRepo.findAll().size());
		Assertions.assertTrue(productRepo.findAll().size()==1);
		
		
	}
	
	private ProductRequest getProductRequest()
	{
		return ProductRequest.builder().name("pixel7").description("pixel7").price(BigDecimal.valueOf(100)).build();
	}

}
