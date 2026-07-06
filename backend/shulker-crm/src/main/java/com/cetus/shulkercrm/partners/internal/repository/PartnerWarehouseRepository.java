package com.cetus.shulkercrm.partners.internal.repository;

import com.cetus.shulkercrm.partners.internal.entity.PartnerWarehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartnerWarehouseRepository extends JpaRepository<PartnerWarehouse, Long> {
    Page<PartnerWarehouse> findAllByPartnerId(Long partnerId, Pageable pageable);

    Optional<PartnerWarehouse> findByIdAndPartnerId(Long id, Long partnerId);
}
