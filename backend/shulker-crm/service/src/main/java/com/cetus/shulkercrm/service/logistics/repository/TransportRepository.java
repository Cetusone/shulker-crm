package com.cetus.shulkercrm.service.logistics.repository;

import com.cetus.shulkercrm.service.logistics.entity.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportRepository extends JpaRepository<Transport, Long> {
}
