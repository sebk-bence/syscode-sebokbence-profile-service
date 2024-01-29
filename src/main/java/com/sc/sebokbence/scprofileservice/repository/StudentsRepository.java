package com.sc.sebokbence.scprofileservice.repository;

import com.sc.sebokbence.scprofileservice.model.entity.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;

public interface StudentsRepository extends JpaRepository<Student, UUID> {
}
