package com.cetus.shulkercrm.service.inventory.repository;

import com.cetus.shulkercrm.service.inventory.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    @EntityGraph(attributePaths = {"characteristics"})
    List<Product> findAll();

}
