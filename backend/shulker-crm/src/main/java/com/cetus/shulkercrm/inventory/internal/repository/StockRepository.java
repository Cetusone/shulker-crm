package com.cetus.shulkercrm.inventory.internal.repository;

import com.cetus.shulkercrm.inventory.internal.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    List<Stock> findAllByOwnWarehouseId(Long id);
    Optional<Stock> findByWarehouseIdAndProductId(Long ownerWarehouseId, Long productId);

}
