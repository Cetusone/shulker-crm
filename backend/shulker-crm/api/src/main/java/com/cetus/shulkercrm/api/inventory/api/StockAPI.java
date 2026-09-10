package com.cetus.shulkercrm.api.inventory.api;

import com.cetus.shulkercrm.api.common.dto.PageResponse;
import com.cetus.shulkercrm.api.inventory.dto.StockCreateRequest;
import com.cetus.shulkercrm.api.inventory.dto.StockResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/warehouse")
public interface StockAPI {


    @PostMapping("/{id}/stock")
    @ResponseStatus(HttpStatus.CREATED)
    StockResponse addProductOnWarehouse(@PathVariable Long id, @RequestBody @Valid StockCreateRequest stockCreateRequest);

    @GetMapping("/{id}/stock")
    PageResponse<StockResponse> getAllStocks(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size);

}
