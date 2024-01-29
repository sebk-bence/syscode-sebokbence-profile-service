package com.sc.sebokbence.scprofileservice.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.sc.sebokbence.scprofileservice.mapper.*;
import com.sc.sebokbence.scprofileservice.model.entity.*;
import com.sc.sebokbence.scprofileservice.model.rest.*;
import java.util.*;
import org.junit.jupiter.api.*;

class UpdateStudentMapperTest {
  @Test
  void testToUpdatedStudent_allFields() {
    final UUID id = UUID.randomUUID();
    final Student persistedStudent = Student
        .builder()
        .id(id)
        .name("Andrew")
        .email("a@email.com")
        .build();
    final UpdateStudentRequest request = UpdateStudentRequest
        .builder()
        .id(null)
        .name("Ben")
        .email("b@email.com")
        .build();

    final Student updatedStudent = UpdateStudentMapper.toUpdatedStudent(persistedStudent, request);

    assertNotNull(updatedStudent);
    assertEquals(id, updatedStudent.getId());
    assertEquals("Ben", updatedStudent.getName());
    assertEquals("b@email.com", updatedStudent.getEmail());
  }

  @Test
  void testToUpdatedStudent_onlyName() {
    final UUID id = UUID.randomUUID();
    final Student persistedStudent = Student
        .builder()
        .id(id)
        .name("Andrew")
        .email("a@email.com")
        .build();
    final UpdateStudentRequest request = UpdateStudentRequest
        .builder()
        .id(null)
        .name("Ben")
        .email(null)
        .build();

    final Student updatedStudent = UpdateStudentMapper.toUpdatedStudent(persistedStudent, request);

    assertNotNull(updatedStudent);
    assertEquals(id, updatedStudent.getId());
    assertEquals("Ben", updatedStudent.getName());
    assertEquals("a@email.com", updatedStudent.getEmail());
  }

  @Test
  void testToUpdatedStudent_onlyEmail() {
    final UUID id = UUID.randomUUID();
    final Student persistedStudent = Student
        .builder()
        .id(id)
        .name("Andrew")
        .email("a@email.com")
        .build();
    final UpdateStudentRequest request = UpdateStudentRequest
        .builder()
        .id(null)
        .name(null)
        .email("b@email.com")
        .build();

    final Student updatedStudent = UpdateStudentMapper.toUpdatedStudent(persistedStudent, request);

    assertNotNull(updatedStudent);
    assertEquals(id, updatedStudent.getId());
    assertEquals("Andrew", updatedStudent.getName());
    assertEquals("b@email.com", updatedStudent.getEmail());
  }

  @Test
  void testToUpdatedStudent_nothing() {
    final UUID id = UUID.randomUUID();
    final Student persistedStudent = Student
        .builder()
        .id(id)
        .name("Andrew")
        .email("a@email.com")
        .build();
    final UpdateStudentRequest request = UpdateStudentRequest
        .builder()
        .id(null)
        .name(null)
        .email(null)
        .build();

    final Student updatedStudent = UpdateStudentMapper.toUpdatedStudent(persistedStudent, request);

    assertNotNull(updatedStudent);
    assertEquals(id, updatedStudent.getId());
    assertEquals("Andrew", updatedStudent.getName());
    assertEquals("a@email.com", updatedStudent.getEmail());
  }
}
