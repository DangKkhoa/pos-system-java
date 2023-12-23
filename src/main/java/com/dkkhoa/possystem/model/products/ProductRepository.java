package com.dkkhoa.possystem.model.products;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByOrderByCreationDateDesc();

    boolean existsByIdAndSalesTransactionsIsEmpty(long id);
}

