package com.sc.sebokbence.scprofileservice.integration;

import static org.junit.jupiter.api.Assertions.*;

import com.sc.sebokbence.scprofileservice.exception.*;
import com.sc.sebokbence.scprofileservice.model.service.address.*;
import com.sc.sebokbence.scprofileservice.service.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit.jupiter.*;
import org.springframework.web.reactive.function.client.*;

/**
 * Real integration test for upstream Address service.
 * The Address service have to run on the host server configured in application-test.yaml.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class AddressServiceTest {
  @Autowired
  AddressService addressService;

  @Test
  void testGetAddress_whenIdExists_thenSuccess() {
    final String id = "eb281107-c753-44c5-a64b-9cbdf5705f4e";
    final Address result = addressService.getAddress(id);

    assertNotNull(result);
    assertEquals(id, result.getId());
    assertEquals("Budapest, Street 1", result.getAddress());
  }

  @Test
  void testGetAddress_whenIdNotExists_thenFails() {
    final String id = "3fcddfe6-fdce-42e8-9d79-21201eb6f01c";

    WebClientResponseException ex = assertThrows(
        WebClientResponseException.class,
        () -> addressService.getAddress(id),
        "Expected to throw WebClientResponseException, but it didn't"
    );

    assertEquals("Not Found", ex.getStatusText());
  }
}
