package com.cetus.shulkercrm.logistics.internal.repository;

import com.cetus.shulkercrm.logistics.internal.entity.OwnWarehouse;
import com.cetus.shulkercrm.logistics.internal.entity.OwnWarehouseTransport;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnWarehouseTransportRepository extends JpaRepository<OwnWarehouseTransport, Long> {

    @EntityGraph(attributePaths = {"transport"})
    List<OwnWarehouseTransport> findByOwnWarehouseIn(List<OwnWarehouse> warehouses);

    @EntityGraph(attributePaths = {"transport"})
    List<OwnWarehouseTransport> findAllByOwnWarehouseId(Long ownWarehouseId);

    @Modifying
    @Query("DELETE FROM OwnWarehouseTransport t WHERE t.ownWarehouse.id = :warehouseId")
    void deleteAllByOwnWarehouseId(@Param("warehouseId") Long warehouseId);



    boolean existsByTransportIdAndOwnWarehouseId(Long transportId, Long warehouseId);
    @Modifying
    @Query("DELETE FROM OwnWarehouseTransport t WHERE t.transport.id = :transportId AND t.ownWarehouse.id = :warehouseId")
    void deleteByTransportIdAndOwnWarehouseId(@Param("transportId") Long transportId, @Param("warehouseId") Long warehouseId);
}
