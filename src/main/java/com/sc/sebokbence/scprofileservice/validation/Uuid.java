package com.sc.sebokbence.scprofileservice.validation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.*;
import jakarta.validation.constraints.*;
import java.lang.annotation.*;

/**
 * Validation annotation for UUID.
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy={})
@Retention(RUNTIME)
@Pattern(regexp="^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$")
@ReportAsSingleViolation
public @interface Uuid {
  String message() default "Invalid UUID";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}