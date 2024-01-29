package com.sc.sebokbence.scprofileservice.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.sc.sebokbence.scprofileservice.controller.*;
import com.sc.sebokbence.scprofileservice.exception.*;
import com.sc.sebokbence.scprofileservice.model.entity.*;
import com.sc.sebokbence.scprofileservice.model.rest.*;
import com.sc.sebokbence.scprofileservice.repository.*;
import java.util.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

@ExtendWith(MockitoExtension.class)
class ProfileControllerTest {
  @Mock
  StudentsRepository studentsRepository;
  @InjectMocks
  ProfileController profileController;

  @Test
  void whenGetAll_thenSucceed() {
    when(studentsRepository.findAll())
        .thenReturn(List.of(
            Student.builder().name("Andrew").email("a@email.com").build(),
            Student.builder().name("Ben").email("b@email.com").build()
        ));

    List<Student> result = profileController.getAll();

    verify(studentsRepository, times(1)).findAll();
    assertEquals(2, result.size());
    assertEquals("Andrew", result.get(0).getName());
    assertEquals("a@email.com", result.get(0).getEmail());
    assertEquals("Ben", result.get(1).getName());
    assertEquals("b@email.com", result.get(1).getEmail());
  }

  @Test
  void whenSave_thenSucceed() {
    final Student student = Student.builder().name("Andrew").email("a@email.com").build();
    when(studentsRepository.save(student))
        .thenReturn(student);

    Student result = profileController.createStudent(
        NewStudentRequest
            .builder()
            .name("Andrew")
            .email("a@email.com")
            .build()
    );

    verify(studentsRepository, times(1)).save(student);
    assertEquals("Andrew", result.getName());
    assertEquals("a@email.com", result.getEmail());
  }

  @Test
  void givenStudentNotExist_whenSave_thenThrowsException() {
    final Student student = Student.builder().name("Andrew").email("a@email.com").build();
    when(studentsRepository.findById(any(UUID.class)))
        .thenReturn(Optional.empty());

    assertThrows(
        NotFoundException.class,
        () -> profileController.updateStudent(
            UpdateStudentRequest
                .builder()
                .id(UUID.randomUUID().toString())
                .name("Andrew")
                .email("a@email.com")
                .build()
        ),
        "Expected to throw NotFoundException, but it didn't"
    );

    verify(studentsRepository, times(1)).findById(any(UUID.class));
    verify(studentsRepository, times(0)).save(any());
  }

  @Test
  void givenStudentExist_whenSave_thenThrowsException() {
    final UUID id = UUID.randomUUID();
    final Student student = Student.builder().id(id).name("Andrew").email("a@email.com").build();
    final Student updatedStudent = Student.builder().id(id).name("Andrew2").email("a2@email.com").build();
    when(studentsRepository.findById(id))
        .thenReturn(Optional.of(student));
    when(studentsRepository.save(updatedStudent))
        .thenReturn(updatedStudent);

    Student result = profileController.updateStudent(
        UpdateStudentRequest
            .builder()
            .id(id.toString())
            .name("Andrew2")
            .email("a2@email.com")
            .build()
    );

    verify(studentsRepository, times(1)).findById(id);
    verify(studentsRepository, times(1)).save(updatedStudent);
    assertEquals("Andrew2", result.getName());
    assertEquals("a2@email.com", result.getEmail());
  }

  @Test
  void givenStudentExist_whenDelete_thenSucceed() {
    final UUID id = UUID.randomUUID();
    when(studentsRepository.existsById(id))
        .thenReturn(true);

    profileController.deleteStudent(id.toString());

    verify(studentsRepository, times(1)).deleteById(id);
  }

  @Test
  void givenStudentNotExist_whenDelete_thenSucceed() {
    final UUID id = UUID.randomUUID();
    when(studentsRepository.existsById(id))
        .thenReturn(false);

    assertThrows(
        NotFoundException.class,
        () -> profileController.deleteStudent(id.toString()),
        "Expected to throw NotFoundException, but it didn't"
    );

    verify(studentsRepository, times(0)).deleteById(id);
  }
}
