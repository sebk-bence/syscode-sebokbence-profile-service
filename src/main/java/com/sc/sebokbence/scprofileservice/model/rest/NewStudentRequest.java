package com.sc.sebokbence.scprofileservice.model.rest;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
public class NewStudentRequest {
  @NotBlank
  private String name;
  @Email
  private String email;
}
