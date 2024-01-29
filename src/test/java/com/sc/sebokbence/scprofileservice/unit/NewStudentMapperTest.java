package com.sc.sebokbence.scprofileservice.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.sc.sebokbence.scprofileservice.mapper.*;
import com.sc.sebokbence.scprofileservice.model.entity.*;
import com.sc.sebokbence.scprofileservice.model.rest.*;
import org.junit.jupiter.api.*;

class NewStudentMapperTest {
  @Test
  void testToEntity() {
    final NewStudentRequest request = NewStudentRequest
        .builder()
        .name("Andrew")
        .email("andrew@email.com")
        .build();

    final Student newStudent = NewStudentMapper.toEntity(request);

    assertNotNull(newStudent);
    assertNull(newStudent.getId());
    assertEquals("Andrew", newStudent.getName());
    assertEquals("andrew@email.com", newStudent.getEmail());
  }
}
