/*package com.cetus.shulkercrm.api.logistics;

import com.cetus.shulkercrm.logistics.api.OwnWarehouseServiceInterface;
import com.cetus.shulkercrm.logistics.api.dto.OwnWarehouseCreateRequest;
import com.cetus.shulkercrm.logistics.api.dto.OwnWarehouseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/own-warehouses")
@Slf4j
@RequiredArgsConstructor
public class OwnWarehouseController {

    OwnWarehouseServiceInterface ownWareHouseService;

    @PostMapping
    public OwnWarehouseResponse createWareHouse(OwnWarehouseCreateRequest request) {
        log.info("createWareHouse", request);
        return ownWareHouseService.createWarehouse(request);
    }

}
*/