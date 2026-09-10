package com.cetus.shulkercrm.service.partners.controller;


import com.cetus.shulkercrm.api.common.dto.PageResponse;
import com.cetus.shulkercrm.api.partners.api.PartnersAPI;
import com.cetus.shulkercrm.api.partners.dto.PartnerCreateRequest;
import com.cetus.shulkercrm.api.partners.dto.PartnerResponse;
import com.cetus.shulkercrm.service.common.PageMapper;
import com.cetus.shulkercrm.service.partners.service.PartnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class PartnersController implements PartnersAPI {

    private final PartnerService partnerService;

    @Override
    public PartnerResponse createPartner(PartnerCreateRequest request) {
        log.info("createPartner");
        return partnerService.createPartner(request);
    }

    @Override
    public PageResponse<PartnerResponse> getPartners(int page, int size) {
        log.info("getPartners,  page: {}, size: {}", page, size);
        return PageMapper.toResponse(partnerService.getPartners(PageRequest.of(page, size)));
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