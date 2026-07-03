package com.cetus.shulkercrm.partners.internal.repository;

import com.cetus.shulkercrm.partners.internal.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {
}
