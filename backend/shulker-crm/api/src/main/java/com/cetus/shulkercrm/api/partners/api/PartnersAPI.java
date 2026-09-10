package com.cetus.shulkercrm.api.partners.api;


import com.cetus.shulkercrm.api.common.dto.PageResponse;
import com.cetus.shulkercrm.api.partners.dto.PartnerCreateRequest;
import com.cetus.shulkercrm.api.partners.dto.PartnerResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/partners")
public interface PartnersAPI {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PartnerResponse createPartner(@Valid @RequestBody PartnerCreateRequest request);

    @GetMapping
    PageResponse<PartnerResponse> getPartners(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size);


    @GetMapping("/{id}")
    PartnerResponse getPartnerById(@PathVariable long id);

    @PutMapping("/{id}")
    PartnerResponse updatePartnerById(@PathVariable long id, @Valid @RequestBody PartnerCreateRequest request);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletePartnerById(@PathVariable long id);

}
