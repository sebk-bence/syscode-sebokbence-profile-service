package com.sc.sebokbence.scprofileservice.mapper;

import com.sc.sebokbence.scprofileservice.model.entity.*;
import com.sc.sebokbence.scprofileservice.model.rest.*;

public class NewStudentMapper {
  public static Student toEntity(NewStudentRequest request) {
    return Student
        .builder()
        .name(request.getName())
        .email(request.getEmail())
        .build();
  }
}
