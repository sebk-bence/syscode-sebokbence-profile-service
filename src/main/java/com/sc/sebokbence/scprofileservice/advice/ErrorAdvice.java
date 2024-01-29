package com.sc.sebokbence.scprofileservice.advice;

import com.sc.sebokbence.scprofileservice.exception.*;
import org.springframework.http.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.*;
import org.springframework.web.servlet.mvc.method.annotation.*;

@RestControllerAdvice
public class ErrorAdvice extends ResponseEntityExceptionHandler {
  /**
   * Handle errors where the request was invalid.
   * @param ex Exception.
   * @return BAD_REQUEST.
   */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status,
      WebRequest request
  ) {
    return ResponseEntity.badRequest().build();
  }

  /**
   * Handle errors where the requested resource was not found.
   * @param ex Exception.
   * @return empty response with status code NOT_FOUND.
   */
  @ExceptionHandler(value = NotFoundException.class)
  ResponseEntity<?> handleNotFoundException(NotFoundException ex) {
    return ResponseEntity.notFound().build();
  }
}
