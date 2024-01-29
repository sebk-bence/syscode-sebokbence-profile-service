package com.sc.sebokbence.scprofileservice.integration;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;
import com.sc.sebokbence.scprofileservice.*;
import com.sc.sebokbence.scprofileservice.model.entity.*;
import com.sc.sebokbence.scprofileservice.model.rest.*;
import java.util.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit.jupiter.*;
import org.springframework.test.web.servlet.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ScProfileServiceApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("integration-test")
class ProfileServiceIT {
  private static final ObjectMapper MAPPER = new ObjectMapper();

  @Autowired
  private MockMvc mvc;

  @Test
  @Order(0)
  void contextLoads() {
  }

  @Test
  @Order(1)
  void testGetAllStudents() throws Exception {
    MvcResult result = mvc
        .perform(get("/students").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andReturn();

    final String contentString = result.getResponse().getContentAsString();
    final List<Student> content = MAPPER.readValue(contentString, new TypeReference<>() {});

    assertEquals(2, content.size());
    assertEquals("Andrew", content.get(0).getName());
    assertEquals("andrew@email.com", content.get(0).getEmail());
    assertEquals("Ben", content.get(1).getName());
    assertEquals("ben@email.com", content.get(1).getEmail());
  }

  @Test
  @Order(2)
  void testCreateNewStudent() throws Exception {
    final NewStudentRequest newStudent =
        NewStudentRequest
            .builder()
            .name("Chris")
            .email("chris@email.com")
            .build();
    final String requestContent = MAPPER.writeValueAsString(newStudent);

    MvcResult mvcResultSave = mvc
        .perform(post("/students").contentType(MediaType.APPLICATION_JSON).content(requestContent))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andReturn();

    MvcResult mvcResultLoad = mvc
        .perform(get("/students").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andReturn();

    final String contentStringSave = mvcResultSave.getResponse().getContentAsString();
    final String contentStringLoad = mvcResultLoad.getResponse().getContentAsString();
    final Student contentSave = MAPPER.readValue(contentStringSave, Student.class);
    final List<Student> contentLoad = MAPPER.readValue(
        contentStringLoad, new TypeReference<List<Student>>() {
        });

    assertEquals(3, contentLoad.size());
    assertEquals("Chris", contentSave.getName());
    assertEquals("chris@email.com", contentSave.getEmail());
    assertEquals("Chris", contentLoad.get(2).getName());
    assertEquals("chris@email.com", contentLoad.get(2).getEmail());
  }

  @Test
  @Order(3)
  void testUpdateStudent() throws Exception {
    // Get and assert values before update
    MvcResult mvcResultLoad = mvc
        .perform(get("/students").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andReturn();

    final String contentStringLoad = mvcResultLoad.getResponse().getContentAsString();
    final List<Student> contentLoad = MAPPER.readValue(contentStringLoad, new TypeReference<>() {});

    assertEquals(3, contentLoad.size());
    assertEquals("Chris", contentLoad.get(2).getName());
    assertEquals("chris@email.com", contentLoad.get(2).getEmail());

    // Update
    final String updateId = contentLoad.get(2).getId().toString();
    final UpdateStudentRequest updatedStudent =
        UpdateStudentRequest
            .builder()
            .id(updateId)
            .name("Daniel")
            .email("daniel@email.com")
            .build();
    final String requestContent = MAPPER.writeValueAsString(updatedStudent);

    MvcResult mvcResultUpdate = mvc
        .perform(put("/students").contentType(MediaType.APPLICATION_JSON).content(requestContent))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andReturn();

    final String contentStringUpdate = mvcResultUpdate.getResponse().getContentAsString();
    final Student contentUpdate = MAPPER.readValue(contentStringUpdate, Student.class);

    assertEquals(updateId, contentUpdate.getId().toString());
    assertEquals("Daniel", contentUpdate.getName());
    assertEquals("daniel@email.com", contentUpdate.getEmail());
  }

  @Test
  @Order(4)
  void testUpdateStudent_emailOnly() throws Exception {
    // Get and assert values before update
    MvcResult mvcResultLoad = mvc
        .perform(get("/students").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andReturn();

    final String contentStringLoad = mvcResultLoad.getResponse().getContentAsString();
    final List<Student> contentLoad = MAPPER.readValue(contentStringLoad, new TypeReference<>() {});

    assertEquals(3, contentLoad.size());
    assertEquals("Daniel", contentLoad.get(2).getName());
    assertEquals("daniel@email.com", contentLoad.get(2).getEmail());

    // Update
    final String updateId = contentLoad.get(2).getId().toString();
    final UpdateStudentRequest updatedStudent =
        UpdateStudentRequest
            .builder()
            .id(updateId)
            .name("Daniel")
            .email("danielNew@email.com")
            .build();
    final String requestContent = MAPPER.writeValueAsString(updatedStudent);

    MvcResult mvcResultUpdate = mvc
        .perform(put("/students").contentType(MediaType.APPLICATION_JSON).content(requestContent))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andReturn();

    final String contentStringUpdate = mvcResultUpdate.getResponse().getContentAsString();
    final Student contentUpdate = MAPPER.readValue(contentStringUpdate, Student.class);

    assertEquals(updateId, contentUpdate.getId().toString());
    assertEquals("Daniel", contentUpdate.getName());
    assertEquals("danielNew@email.com", contentUpdate.getEmail());
  }

  @Test
  @Order(5)
  void testDeleteStudent() throws Exception {
    // Get and assert values before delete
    MvcResult mvcResultLoadBefore = mvc
        .perform(get("/students").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andReturn();

    final String contentStringLoad = mvcResultLoadBefore.getResponse().getContentAsString();
    final List<Student> contentLoad = MAPPER.readValue(contentStringLoad, new TypeReference<>() {});

    assertEquals(3, contentLoad.size());

    // Delete
    final String deleteId = contentLoad.get(2).getId().toString();
    mvc
        .perform(delete("/students/" + deleteId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    // Get and assert values after delete
    MvcResult mvcResultLoadAfter = mvc
        .perform(get("/students").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andReturn();

    final String contentStringLoadAfter = mvcResultLoadAfter.getResponse().getContentAsString();
    final List<Student> contentLoadAfter = MAPPER.readValue(contentStringLoadAfter, new TypeReference<>() {});

    assertEquals(2, contentLoadAfter.size());
  }

  @Test
  @Order(100)
  void testUpdateStudent_withInvalidId() throws Exception {
    final String invalidUuid = "bf223b0b-efea-4c70-8fee-f395f645f21d";
    final UpdateStudentRequest updatedStudent =
        UpdateStudentRequest
            .builder()
            .id(invalidUuid)
            .name("Daniel")
            .email("daniel@email.com")
            .build();
    final String requestContent = MAPPER.writeValueAsString(updatedStudent);

    mvc
        .perform(put("/students").contentType(MediaType.APPLICATION_JSON).content(requestContent))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(101)
  void testDeleteStudent_withInvalidId() throws Exception {
    final String invalidUuid = "bf223b0b-efea-4c70-8fee-f395f645f21d";
    mvc
        .perform(delete("/students/" + invalidUuid).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(102)
  void testCreateNewStudent_withInvalidRequest() throws Exception {
    mvc
        .perform(post("/students").contentType(MediaType.APPLICATION_JSON)) // no content
        .andExpect(status().isBadRequest());
  }

  @Test
  @Order(103)
  void testCreateNewStudent_withInvalidEmail() throws Exception {
    final NewStudentRequest newStudent =
        NewStudentRequest
            .builder()
            .name("Chris")
            .email("invalid_email")
            .build();
    final String requestContent = MAPPER.writeValueAsString(newStudent);

    mvc
        .perform(post("/students").contentType(MediaType.APPLICATION_JSON).content(requestContent))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Order(104)
  void testUpdateStudent_withInvalidRequest() throws Exception {
    mvc
        .perform(put("/students").contentType(MediaType.APPLICATION_JSON)) // no content
        .andExpect(status().isBadRequest());

  }
}
