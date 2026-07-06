package com.cetus.shulkercrm.partners.internal.service;

import com.cetus.shulkercrm.partners.api.PartnerServiceInterface;
import com.cetus.shulkercrm.partners.api.dto.PartnerCreateRequest;
import com.cetus.shulkercrm.partners.api.dto.PartnerResponse;
import com.cetus.shulkercrm.partners.internal.entity.Partner;
import com.cetus.shulkercrm.partners.internal.repository.PartnerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PartnerService implements PartnerServiceInterface {

    private final PartnerRepository partnerRepository;

    @Override
    @Transactional
    public PartnerResponse createPartner(PartnerCreateRequest request) {
        log.debug("Создание партнёра: {}", request.name());

        Partner partner = Partner.builder()
                .name(request.name())
                .apiKey(request.apiKey())
                .contactEmail(request.contactEmail())
                .isActive(request.isActive() != null ? request.isActive() : true)
                .build();

        return mapToResponse(partnerRepository.save(partner));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PartnerResponse> getPartners(Pageable pageable) {
        log.debug("Запрос списка всех партнёров");
        Page<Partner> partners = partnerRepository.findAll(pageable);
        return partners.map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public PartnerResponse getPartnerById(long id) {
        log.debug("Запрос партнёра с ID: {}", id);

        Partner partner = partnerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Партнёр с ID " + id + " не найден"));

        return mapToResponse(partner);
    }

    @Override
    @Transactional
    public PartnerResponse updatePartnerById(long id, PartnerCreateRequest request) {
        log.debug("Обновление партнёра с ID: {}", id);

        Partner partner = partnerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Партнёр с ID " + id + " не найден"));

        partner.setName(request.name());
        partner.setApiKey(request.apiKey());
        partner.setContactEmail(request.contactEmail());

        if (request.isActive() != null) {
            partner.setIsActive(request.isActive());
        }
        return mapToResponse(partnerRepository.save(partner));
    }

    @Override
    @Transactional
    public void deletePartnerById(long id) {
        log.debug("Удаление партнёра с ID: {}", id);

        //  ДОДЕЛАТЬ!
    }

    private PartnerResponse mapToResponse(Partner partner) {
        return new PartnerResponse(
                partner.getId(),
                partner.getName(),
                partner.getContactEmail(),
                partner.getIsActive(),
                partner.getCreatedAt(),
                partner.getUpdatedAt()
        );
    }
}