package com.cetus.shulkercrm.api.inventory;

import com.cetus.shulkercrm.inventory.api.StockServiceInterface;
import com.cetus.shulkercrm.inventory.api.dto.StockCreateRequest;
import com.cetus.shulkercrm.inventory.api.dto.StockResponse;
import com.cetus.shulkercrm.api.exception.WarehouseNotFoundException;
import com.cetus.shulkercrm.inventory.internal.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/warehouse")
public class StockController {

    private final StockServiceInterface stockService;

    @PostMapping("/{id}/stock")
    @ResponseStatus(HttpStatus.CREATED)
    public StockResponse addProductOnWarehouse(@PathVariable Long id, @RequestBody @Valid StockCreateRequest stockCreateRequest) {
        log.info("Adding product on Warehouse {}", id);
        StockResponse response = stockService.addProductOnWarehouse(id, stockCreateRequest);
        if (response == null) {
            throw new WarehouseNotFoundException(id);
        }
        return response;
    }

    @GetMapping("/{id}/stock")
    public List<StockResponse> getAllStocks(@PathVariable Long id) {
        log.info("Getting stocks from Warehouse {}", id);
        List<StockResponse> stocks = stockService.getAllStocks(id);
        if (stocks == null) {
            throw new WarehouseNotFoundException(id);
        }
        return stocks;
    }
}
