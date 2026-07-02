package com.cetus.shulkercrm.inventory.internal.repository;

import com.cetus.shulkercrm.inventory.internal.entity.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
}
