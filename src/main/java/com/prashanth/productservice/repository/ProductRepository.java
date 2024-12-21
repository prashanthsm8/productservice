package com.prashanth.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.prashanth.productservice.model.Product;

public interface ProductRepository extends MongoRepository<Product,String>{

}
