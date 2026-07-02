package com.cetus.shulkercrm.api.logistics;

import com.cetus.shulkercrm.logistics.api.TransportServiceInterface;
import com.cetus.shulkercrm.logistics.api.dto.TransportCreateRequest;
import com.cetus.shulkercrm.logistics.api.dto.TransportResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transports")
@Slf4j
@RequiredArgsConstructor
public class TransportController {

    private final TransportServiceInterface transportService;

    @PostMapping
    public TransportResponse createTransport(@RequestBody @Valid TransportCreateRequest request){
        log.info("createTransport {}", request);
        return transportService.createTransport(request);
    }

    @GetMapping
    public List<TransportResponse> getAllTransports(){
        log.info("getAllTransports");
        return transportService.getAllTransports();
    }

    @GetMapping("/{id}")
    public TransportResponse getTransportById(@PathVariable Long id){
        log.info("getTransportById {}", id);
        return transportService.getTransportById(id);
    }

    @PutMapping("/{id}")
    public TransportResponse updateTransportById(@PathVariable Long id, @RequestBody @Valid TransportCreateRequest request){
        log.info("updateTransportById {}", request);
        return transportService.updateTransportById(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteTransportById(@PathVariable Long id){
        log.info("deleteTransportById {}", id);
        transportService.deleteTransportById(id);
    }

    @PostMapping("/{transportId}/warehouse/{warehouseId}")
    public void linkTransport(@PathVariable Long transportId, @PathVariable Long warehouseId){
        log.info("linkTransportById {}", transportId);
        transportService.linkTransport(transportId, warehouseId);
    }

    @DeleteMapping("/{transportId}/warehouse/{warehouseId}")
    public void unlinkTransport(@PathVariable Long transportId, @PathVariable Long warehouseId){
        log.info("unlinkTransportById {}", transportId);
        transportService.unlinkTransport(transportId, warehouseId);
    }

}
