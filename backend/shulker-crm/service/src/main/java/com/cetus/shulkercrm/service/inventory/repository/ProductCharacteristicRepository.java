package com.cetus.shulkercrm.service.inventory.repository;

import com.cetus.shulkercrm.service.inventory.entity.ProductCharacteristic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCharacteristicRepository extends JpaRepository<ProductCharacteristic, Long> {


}
