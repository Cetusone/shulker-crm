package com.cetus.shulkercrm.inventory.api.controller;

import com.cetus.shulkercrm.inventory.api.dto.StockCreateRequest;
import com.cetus.shulkercrm.inventory.api.dto.StockResponse;
import com.cetus.shulkercrm.inventory.internal.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/warehouse")
public class StockController {

    private final StockService stockService;

    @PostMapping("/{id}/stock")
    @ResponseStatus(HttpStatus.CREATED)
    public StockResponse addProductOnWarehouse(@PathVariable Long id, @RequestBody @Valid StockCreateRequest stockCreateRequest) {
        log.info("Adding product on Warehouse {}", id);
        return stockService.addProductOnWarehouse(id, stockCreateRequest);
    }

    @GetMapping("/{id}/stock")
    public Page<StockResponse> getAllStocks(@PathVariable Long id, Pageable pageable) {
        log.info("Getting stocks from Warehouse {}", id);
        return stockService.getAllStocks(id, pageable);

    }
}
