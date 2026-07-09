package com.cetus.shulkercrm.service.logistics.repository;


import com.cetus.shulkercrm.service.logistics.entity.OwnWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnWarehouseRepository extends JpaRepository<OwnWarehouse, Long> {
}
