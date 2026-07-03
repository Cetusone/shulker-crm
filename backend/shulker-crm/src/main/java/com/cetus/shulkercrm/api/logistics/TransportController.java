package com.cetus.shulkercrm.api.logistics;

import com.cetus.shulkercrm.logistics.api.TransportServiceInterface;
import com.cetus.shulkercrm.logistics.api.dto.TransportCreateRequest;
import com.cetus.shulkercrm.logistics.api.dto.TransportResponse;
import com.cetus.shulkercrm.api.exception.TransportNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transports")
@Slf4j
@RequiredArgsConstructor
public class TransportController {

    private final TransportServiceInterface transportService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
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
        TransportResponse response = transportService.getTransportById(id);
        if (response == null) {
            throw new TransportNotFoundException(id);
        }
        return response;
    }

    @PutMapping("/{id}")
    public TransportResponse updateTransportById(@PathVariable Long id, @RequestBody @Valid TransportCreateRequest request){
        log.info("updateTransportById {}", request);
        TransportResponse response = transportService.updateTransportById(id, request);
        if (response == null) {
            throw new TransportNotFoundException(id);
        }
        return response;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTransportById(@PathVariable Long id){
        log.info("deleteTransportById {}", id);
        transportService.deleteTransportById(id);
    }

    @PostMapping("/{transportId}/warehouse/{warehouseId}")
    @ResponseStatus(HttpStatus.OK)
    public void linkTransport(@PathVariable Long transportId, @PathVariable Long warehouseId){
        log.info("linkTransportById {}", transportId);
        transportService.linkTransport(transportId, warehouseId);
    }

    @DeleteMapping("/{transportId}/warehouse/{warehouseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unlinkTransport(@PathVariable Long transportId, @PathVariable Long warehouseId){
        log.info("unlinkTransportById {}", transportId);
        transportService.unlinkTransport(transportId, warehouseId);
    }

}
