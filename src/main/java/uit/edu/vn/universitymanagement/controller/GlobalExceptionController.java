package uit.edu.vn.universitymanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uit.edu.vn.universitymanagement.dto.ExceptionDto;
import uit.edu.vn.universitymanagement.exception.PermissionDeniedException;
import uit.edu.vn.universitymanagement.exception.ResourceNotFoundException;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ExceptionDto> handleCommonException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionDto(new Date(), HttpStatus.INTERNAL_SERVER_ERROR, "internal erver error"));
    }

    @ExceptionHandler(value = {BadCredentialsException.class, DisabledException.class})
    public ResponseEntity<ExceptionDto> handleBadCredentialsException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionDto(new Date(), HttpStatus.UNAUTHORIZED, "bad credentials"));
    }

    @ExceptionHandler(value = PermissionDeniedException.class)
    public ResponseEntity<ExceptionDto> handlePermissionDeniedException() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionDto(new Date(), HttpStatus.FORBIDDEN, "permission denied"));
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleResourceNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDto(new Date(), HttpStatus.NOT_FOUND, "not found"));
    }
}
