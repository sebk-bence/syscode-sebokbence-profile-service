package com.sc.sebokbence.scprofileservice.config;

import static org.springframework.http.MediaType.*;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.web.reactive.function.client.*;

@Configuration
@Getter
public class AddressServiceConfig {
  @Value("${services.address.server}")
  private String server;
  @Value("${services.address.auth.user}")
  private String authUser;
  @Value("${services.address.auth.password}")
  private String authPassword; // Optimally it is not stored in a field
  @Value("${services.address.endpoints.getAddress}")
  private String getAddressEndpoint;

  @Bean
  public WebClient addressServiceClient() {
    return WebClient
        .builder()
        .baseUrl(server)
        .defaultHeaders(httpHeaders -> {
          httpHeaders.setContentType(APPLICATION_JSON);
          httpHeaders.setBasicAuth(authUser, authPassword);
        })
        .build();
  }
}
