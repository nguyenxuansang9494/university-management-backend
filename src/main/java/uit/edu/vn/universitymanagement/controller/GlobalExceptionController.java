package uit.edu.vn.universitymanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uit.edu.vn.universitymanagement.dto.ExceptionDto;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ExceptionDto> handleCommonException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionDto(new Date(), HttpStatus.INTERNAL_SERVER_ERROR, "internal erver error"));
    }
}
