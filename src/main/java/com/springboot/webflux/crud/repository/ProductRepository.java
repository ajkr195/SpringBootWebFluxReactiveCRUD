package com.springboot.webflux.crud.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.springboot.webflux.crud.model.Product;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {
}
