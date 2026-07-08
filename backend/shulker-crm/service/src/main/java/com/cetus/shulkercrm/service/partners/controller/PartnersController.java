package com.cetus.shulkercrm.service.partners.controller;


import com.cetus.shulkercrm.api.partners.api.PartnersAPI;
import com.cetus.shulkercrm.api.partners.dto.PartnerCreateRequest;
import com.cetus.shulkercrm.api.partners.dto.PartnerResponse;
import com.cetus.shulkercrm.service.partners.service.PartnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PartnersController implements PartnersAPI {

    private final PartnerService partnerService;

    @Override
    public PartnerResponse createPartner(PartnerCreateRequest request) {
        log.info("createPartner");
        return partnerService.createPartner(request);
    }

    @Override
    public Page<PartnerResponse> getPartners(Pageable pageable) {
        log.info("getPartners");
        return partnerService.getPartners(pageable);
    }

    @Override
    public PartnerResponse getPartnerById(long id) {
        log.info("getPartnerById");
        return partnerService.getPartnerById(id);
    }

    @Override
    public PartnerResponse updatePartnerById(long id, PartnerCreateRequest request) {
        log.info("updatePartnerById");
        return partnerService.updatePartnerById(id, request);
    }

    @Override
    public void deletePartnerById(long id) {
        log.info("deletePartnerById");
        partnerService.deletePartnerById(id);
    }
}