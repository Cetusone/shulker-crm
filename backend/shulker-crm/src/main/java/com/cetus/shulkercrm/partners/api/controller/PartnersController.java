package com.cetus.shulkercrm.partners.api.controller;

import com.cetus.shulkercrm.partners.api.dto.PartnerCreateRequest;
import com.cetus.shulkercrm.partners.api.dto.PartnerResponse;
import com.cetus.shulkercrm.partners.internal.service.PartnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partners")
@RequiredArgsConstructor
@Slf4j
public class PartnersController {

    private final PartnerService partnerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PartnerResponse createPartner(@Valid @RequestBody PartnerCreateRequest request) {
        log.info("createPartner");
        return partnerService.createPartner(request);
    }

    @GetMapping
    public Page<PartnerResponse> getPartners(Pageable pageable) {
        log.info("getPartners");
        return partnerService.getPartners(pageable);
    }

    @GetMapping("/{id}")
    public PartnerResponse getPartnerById(@PathVariable long id) {
        log.info("getPartnerById");
        return partnerService.getPartnerById(id);
    }

    @PutMapping("/{id}")
    public PartnerResponse updatePartnerById(
            @PathVariable long id,
            @Valid @RequestBody PartnerCreateRequest request
    ) {
        log.info("updatePartnerById");
        return partnerService.updatePartnerById(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePartnerById(@PathVariable long id) {
        log.info("deletePartnerById");
        partnerService.deletePartnerById(id);
    }
}