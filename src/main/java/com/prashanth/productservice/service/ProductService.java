package com.prashanth.productservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.sym.Name;
import com.prashanth.productservice.dto.ProductRequest;
import com.prashanth.productservice.dto.ProductResponse;
import com.prashanth.productservice.model.Product;
import com.prashanth.productservice.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
	
	private final ProductRepository productRepository;
	
	
//	public ProductService(ProductRepository productRepository) {
//		this.productRepository = productRepository;
//	}


	public void createProduct(ProductRequest productRequest)
	{
		Product pr = Product.builder().name(productRequest.getName()).description(productRequest.getDescription())
						.price(productRequest.getPrice()).build();
						
		productRepository.save(pr);
		log.info("product {} is saved" ,pr.getId());
	}



	public List<ProductResponse> getAllProducts() {
		
		List<Product> lp = productRepository.findAll();
		return lp.stream().map(p->maptoProdResponse(p)).toList();	
	}

	public ProductResponse maptoProdResponse(Product pr)
	{
		ProductResponse response= ProductResponse.builder().id(pr.getId()).name(pr.getName())
		.description(pr.getDescription()).price(pr.getPrice()).build();
		return response;
	}
}
