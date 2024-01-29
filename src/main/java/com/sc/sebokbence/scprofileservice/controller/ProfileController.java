package com.sc.sebokbence.scprofileservice.controller;

import com.sc.sebokbence.scprofileservice.exception.*;
import com.sc.sebokbence.scprofileservice.mapper.*;
import com.sc.sebokbence.scprofileservice.model.entity.*;
import com.sc.sebokbence.scprofileservice.model.rest.*;
import com.sc.sebokbence.scprofileservice.repository.*;
import com.sc.sebokbence.scprofileservice.validation.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import jakarta.validation.*;
import jakarta.validation.constraints.*;
import java.util.*;
import lombok.*;
import lombok.extern.log4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/students")
@RequiredArgsConstructor
@Log4j2
public class ProfileController {
  private final StudentsRepository studentsRepository;

  @Operation(summary = "Retrieve all students", tags = { "students" })
  @ApiResponses(
      @ApiResponse(
          responseCode = "200",
          content = { @Content(
              array = @ArraySchema(schema = @Schema(implementation = Student.class)),
              mediaType = "application/json"
          )}
      )
  )
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Student> getAll() {
    log.debug("Get all students called");
    return studentsRepository.findAll();
  }
  // ##########################################################################################
  @Operation(summary = "Create new student", tags = { "students" })
  @ApiResponses( {
      @ApiResponse(
          responseCode = "200",
          content = { @Content(schema = @Schema(implementation = Student.class), mediaType = "application/json") }
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Invalid request",
          content = { @Content(schema = @Schema()) }
      )
  })
  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public Student createStudent(@RequestBody @Valid @NotNull NewStudentRequest request) {
    log.debug("Save new student called: {}", request);
    return studentsRepository.save(NewStudentMapper.toEntity(request));
  }
  // ##########################################################################################
  @Operation(summary = "Update existing student", tags = { "students" })
  @ApiResponses( {
      @ApiResponse(
          responseCode = "200",
          content = { @Content(schema = @Schema(implementation = Student.class), mediaType = "application/json") }
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Invalid request",
          content = { @Content(schema = @Schema()) }
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Student does not exist",
          content = { @Content(schema = @Schema()) }
      )
  })
  @PutMapping
  @ResponseStatus(HttpStatus.OK)
  public Student updateStudent(@RequestBody @Valid @NotNull UpdateStudentRequest request) {
    log.debug("Update student called: {}", request);
    Optional<Student> persistedStudent = studentsRepository.findById(UUID.fromString(request.getId()));
    if(persistedStudent.isEmpty()) {
      log.debug("Update student failed, id does not exist: {}", request.getId());
      throw new NotFoundException();
    }
    Student student = UpdateStudentMapper.toUpdatedStudent(persistedStudent.get(), request);
    return studentsRepository.save(student);
  }
  // ##########################################################################################
  @Operation(summary = "Delete student", tags = { "students" })
  @ApiResponses( {
      @ApiResponse(
          responseCode = "200",
          content = { @Content(schema = @Schema()) }
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Invalid request parameter UUID",
          content = { @Content(schema = @Schema()) }
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Student does not exist",
          content = { @Content(schema = @Schema()) }
      )
  })
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteStudent(@PathVariable @Uuid String id) {
    log.debug("Delete student called with id: {}", id);
    final UUID deleteUuid = UUID.fromString(id);
    if(!studentsRepository.existsById(deleteUuid)) {
      log.debug("Delete student failed, id does not exist: {}", id);
      throw new NotFoundException();
    }
    studentsRepository.deleteById(deleteUuid);
  }
}
