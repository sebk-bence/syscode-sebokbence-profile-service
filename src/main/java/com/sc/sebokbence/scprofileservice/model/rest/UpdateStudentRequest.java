package com.sc.sebokbence.scprofileservice.model.rest;

import com.sc.sebokbence.scprofileservice.validation.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
public class UpdateStudentRequest {
  @Uuid
  private String id;
  private String name;
  @Email
  private String email;
}
