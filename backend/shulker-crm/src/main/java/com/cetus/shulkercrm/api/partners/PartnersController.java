package com.cetus.shulkercrm.api.partners;

import com.cetus.shulkercrm.partners.api.PartnerServiceInterface;
import com.cetus.shulkercrm.partners.api.dto.PartnerCreateRequest;
import com.cetus.shulkercrm.partners.api.dto.PartnerResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partners")
@RequiredArgsConstructor
public class PartnersController {

    private final PartnerServiceInterface partnerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PartnerResponse createPartner(@Valid @RequestBody PartnerCreateRequest request) {
        return partnerService.createPartner(request);
    }

    @GetMapping
    public List<PartnerResponse> getPartners() {
        return partnerService.getPartners();
    }

    @GetMapping("/{id}")
    public PartnerResponse getPartnerById(@PathVariable long id) {
        return partnerService.getPartnerById(id);
    }

    @PutMapping("/{id}")
    public PartnerResponse updatePartnerById(
            @PathVariable long id,
            @Valid @RequestBody PartnerCreateRequest request
    ) {
        return partnerService.updatePartnerById(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePartnerById(@PathVariable long id) {
        partnerService.deletePartnerById(id);
    }
}