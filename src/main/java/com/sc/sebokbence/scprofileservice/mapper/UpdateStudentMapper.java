package com.sc.sebokbence.scprofileservice.mapper;

import com.sc.sebokbence.scprofileservice.model.entity.*;
import com.sc.sebokbence.scprofileservice.model.rest.*;

public class UpdateStudentMapper {
  public static Student toUpdatedStudent(Student persistedStudent, UpdateStudentRequest request) {
    return Student
        .builder()
        .id(persistedStudent.getId())
        .name(
            request.getName() != null
                ? request.getName()
                : persistedStudent.getName()
        )
        .email(
            request.getEmail() != null
                ? request.getEmail()
                : persistedStudent.getEmail()
        )
        .build();
  }
}
