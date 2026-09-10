package com.cetus.shulkercrm.service.inventory.controller;


import com.cetus.shulkercrm.api.common.dto.PageResponse;
import com.cetus.shulkercrm.api.inventory.api.StockAPI;
import com.cetus.shulkercrm.api.inventory.dto.StockCreateRequest;
import com.cetus.shulkercrm.api.inventory.dto.StockResponse;
import com.cetus.shulkercrm.service.common.PageMapper;
import com.cetus.shulkercrm.service.inventory.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
public class StockController implements StockAPI {

    private final StockService stockService;

    @Override
    public StockResponse addProductOnWarehouse(Long id, StockCreateRequest stockCreateRequest) {
        log.info("Adding product on Warehouse {}", id);
        return stockService.addProductOnWarehouse(id, stockCreateRequest);
    }

    @Override
    public PageResponse<StockResponse> getAllStocks(Long id, int page, int size) {
        log.info("Getting stocks from Warehouse {}, page: {}, size: {}", id, page, size);
        return PageMapper.toResponse(stockService.getAllStocks(id, PageRequest.of(page, size)));
    }
}
