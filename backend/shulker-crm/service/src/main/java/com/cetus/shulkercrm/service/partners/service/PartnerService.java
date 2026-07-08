package com.cetus.shulkercrm.service.partners.service;


import com.cetus.shulkercrm.api.partners.dto.PartnerCreateRequest;
import com.cetus.shulkercrm.api.partners.dto.PartnerResponse;
import com.cetus.shulkercrm.service.partners.entity.Partner;
import com.cetus.shulkercrm.service.partners.repository.PartnerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PartnerService {

    private final PartnerRepository partnerRepository;

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

    @Transactional(readOnly = true)
    public Page<PartnerResponse> getPartners(Pageable pageable) {
        log.debug("Запрос списка всех партнёров");
        Page<Partner> partners = partnerRepository.findAll(pageable);
        return partners.map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public PartnerResponse getPartnerById(long id) {
        log.debug("Запрос партнёра с ID: {}", id);

        Partner partner = partnerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Партнёр с ID " + id + " не найден"));

        return mapToResponse(partner);
    }

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

    @Transactional
    public void deletePartnerById(long id) {

        log.debug("Удаление партнёра с ID: {}", id);
        partnerRepository.deleteById(id);
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