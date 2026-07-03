package com.cetus.shulkercrm.partners.api;

import com.cetus.shulkercrm.partners.api.dto.PartnerCreateRequest;
import com.cetus.shulkercrm.partners.api.dto.PartnerResponse;

import java.util.List;

public interface PartnerServiceInterface {

    public PartnerResponse createPartner(PartnerCreateRequest request);
    public List<PartnerResponse> getPartners();
    public PartnerResponse getPartnerById(long id);
    public PartnerResponse updatePartnerById(long id, PartnerCreateRequest request);
    public void deletePartnerById(long id);

}
