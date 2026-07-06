package com.cetus.shulkercrm.inventory.internal.repository;

import com.cetus.shulkercrm.inventory.internal.entity.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    Page<Stock> findAllByOwnWarehouseId(Long id, Pageable pageable);
    Optional<Stock> findByOwnWarehouseIdAndProductId(Long ownWarehouseId, Long productId);

}
