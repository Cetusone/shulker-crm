package com.cetus.shulkercrm.api.inventory.api;

import com.cetus.shulkercrm.api.inventory.dto.StockCreateRequest;
import com.cetus.shulkercrm.api.inventory.dto.StockResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/warehouse")
public interface StockAPI {


    @PostMapping("/{id}/stock")
    @ResponseStatus(HttpStatus.CREATED)
    StockResponse addProductOnWarehouse(@PathVariable Long id, @RequestBody @Valid StockCreateRequest stockCreateRequest);

    @GetMapping("/{id}/stock")
    Page<StockResponse> getAllStocks(@PathVariable Long id, Pageable pageable);

}
