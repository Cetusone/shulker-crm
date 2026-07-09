package com.cetus.shulkercrm.service.partners.repository;

import com.cetus.shulkercrm.service.partners.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {
}
