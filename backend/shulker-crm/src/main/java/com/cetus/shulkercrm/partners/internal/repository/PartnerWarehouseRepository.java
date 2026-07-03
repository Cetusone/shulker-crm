package com.cetus.shulkercrm.partners.internal.repository;

import com.cetus.shulkercrm.partners.internal.entity.PartnerWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartnerWarehouseRepository extends JpaRepository<PartnerWarehouse, Long> {
    List<PartnerWarehouse> findAllByPartnerId(Long partnerId);

    // Найти конкретный склад у конкретного партнёра (для безопасности)
    Optional<PartnerWarehouse> findByIdAndPartnerId(Long id, Long partnerId);
}
