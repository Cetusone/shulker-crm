package com.cetus.shulkercrm.logistics.internal.repository;

import com.cetus.shulkercrm.logistics.internal.entity.OwnWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnWarehouseRepository extends JpaRepository<OwnWarehouse, Long> {
}
