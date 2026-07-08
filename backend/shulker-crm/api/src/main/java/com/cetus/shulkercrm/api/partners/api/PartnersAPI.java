package com.cetus.shulkercrm.api.partners.api;


import com.cetus.shulkercrm.api.partners.dto.PartnerCreateRequest;
import com.cetus.shulkercrm.api.partners.dto.PartnerResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/partners")
public interface PartnersAPI {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PartnerResponse createPartner(@Valid @RequestBody PartnerCreateRequest request);

    @GetMapping
    Page<PartnerResponse> getPartners(Pageable pageable);

    @GetMapping("/{id}")
    PartnerResponse getPartnerById(@PathVariable long id);

    @PutMapping("/{id}")
    PartnerResponse updatePartnerById(@PathVariable long id, @Valid @RequestBody PartnerCreateRequest request);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletePartnerById(@PathVariable long id);

}
