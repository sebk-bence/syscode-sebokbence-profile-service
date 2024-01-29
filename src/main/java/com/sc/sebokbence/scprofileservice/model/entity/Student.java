package com.sc.sebokbence.scprofileservice.model.entity;

import jakarta.persistence.Table;
import jakarta.persistence.*;
import java.util.*;
import lombok.*;
import org.hibernate.annotations.*;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
  @Id
  @UuidGenerator
  private UUID id;

  @Column
  private String name;

  @Column
  private String email;

}
