package com.cetus.shulkercrm.service.partners.service;


import com.cetus.shulkercrm.api.partners.dto.PartnerCreateRequest;
import com.cetus.shulkercrm.api.partners.dto.PartnerResponse;
import com.cetus.shulkercrm.service.partners.entity.Partner;
import com.cetus.shulkercrm.service.partners.mapper.PartnerMapper;
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
    private final PartnerMapper partnerMapper;

    @Transactional
    public PartnerResponse createPartner(PartnerCreateRequest request) {
        log.debug("Создание партнёра: {}", request.name());

        Partner partner = partnerMapper.toEntity(request);

        return partnerMapper.toResponse(partnerRepository.save(partner));
    }

    @Transactional(readOnly = true)
    public Page<PartnerResponse> getPartners(Pageable pageable) {
        log.debug("Запрос списка всех партнёров");
        Page<Partner> partners = partnerRepository.findAll(pageable);
        return partners.map(partnerMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public PartnerResponse getPartnerById(long id) {
        log.debug("Запрос партнёра с ID: {}", id);

        Partner partner = partnerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Партнёр с ID " + id + " не найден"));

        return partnerMapper.toResponse(partner);
    }

    @Transactional
    public PartnerResponse updatePartnerById(long id, PartnerCreateRequest request) {
        log.debug("Обновление партнёра с ID: {}", id);

        Partner partner = partnerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Партнёр с ID " + id + " не найден"));

        partnerMapper.updateFromRequest(request, partner);

        return partnerMapper.toResponse(partnerRepository.save(partner));
    }

    @Transactional
    public void deletePartnerById(long id) {

        log.debug("Удаление партнёра с ID: {}", id);
        partnerRepository.deleteById(id);
    }
}