package com.springboot.webflux.crud;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.test.context.junit4.SpringRunner;

import com.springboot.webflux.crud.model.Product;
import com.springboot.webflux.crud.repository.ProductRepository;

import io.r2dbc.h2.H2ConnectionFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest
public class R2dbcApplicationIntegrationTest {


    @Autowired
    ProductRepository productRepository;

    @Autowired
    DatabaseClient client;

    @Autowired
    H2ConnectionFactory factory;


    @Before
    public void setup() {

        Hooks.onOperatorDebug();

        List<String> statements = Arrays.asList(//
                "DROP TABLE IF EXISTS product;",
                "CREATE table product (id INT AUTO_INCREMENT NOT NULL, description VARCHAR2, price DOUBLE NOT NULL);");

        statements.forEach(it -> client.execute(it) //
                .fetch() //
                .rowsUpdated() //
                .as(StepVerifier::create) //
                .expectNextCount(1) //
                .verifyComplete());

    }

    @Test
    public void whenDeleteAll_then0IsExpected() {


        productRepository.deleteAll()
                .as(StepVerifier::create)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void whenInsert6_then6AreExpected() {

        insertProducts();

        productRepository.findAll()
                .as(StepVerifier::create)
                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    public void whenSearchForCR7_then1IsExpected() {

        insertProducts();

        productRepository.findAll()
                .as(StepVerifier::create)
                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    public void whenSearchForId1_then1IsExpected() {
        insertProducts();

        productRepository.findById(1)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    public void whenBatchHas12perations_then12AreExpected() {
        Mono.from(factory.create())
          .flatMapMany(connection -> Flux.from(connection
            .createBatch()
            .add("select * from product")
            .add("select * from product")
            .execute()))
          .as(StepVerifier::create)
          .expectNextCount(12)
          .verifyComplete();

    }

    private void insertProducts() {
        List<Product> products = Arrays.asList(
                new Product(null, "Description 1", (double) 37),
                new Product(null, "Description 2", (double) 32),
                new Product(null, "Description 3", (double) 20),
                new Product(null, "Description 4", (double) 34),
                new Product(null, "Description 5", (double) 30),
                new Product(null, "Description 6", (double) 32)
        );

        productRepository.saveAll(products).subscribe();
    }
}