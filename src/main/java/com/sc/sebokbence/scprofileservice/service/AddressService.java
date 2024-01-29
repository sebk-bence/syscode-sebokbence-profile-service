package com.sc.sebokbence.scprofileservice.service;

import com.sc.sebokbence.scprofileservice.config.*;
import com.sc.sebokbence.scprofileservice.model.service.address.*;
import java.util.*;
import lombok.*;
import lombok.extern.log4j.*;
import org.springframework.stereotype.*;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class AddressService {
  private final AddressServiceConfig config;
  private final WebClient addressServiceClient;

  public Address getAddress(String id) {
    log.debug("Call upstream Address service: address/{}", id);

    return addressServiceClient
        .get()
        .uri(uriBuilder -> uriBuilder
            .path(config.getGetAddressEndpoint())
            .build(id)
        )
        .retrieve()
        .bodyToMono(Address.class)
        .onErrorResume(e -> {
          log.error("Error while calling address service /address/{}: {}", id, e);
          return Mono.error(e);
        })
        .block();
  }

  public Address getAddress(UUID id) {
    return getAddress(id.toString());
  }
}
