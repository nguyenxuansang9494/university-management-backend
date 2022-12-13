package uit.edu.vn.universitymanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uit.edu.vn.universitymanagement.dto.ExceptionDto;
import uit.edu.vn.universitymanagement.exception.CommonRuntimeException;
import uit.edu.vn.universitymanagement.exception.ErrorType;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ExceptionDto> handleException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionDto(new Date(), HttpStatus.INTERNAL_SERVER_ERROR, "internal server error"));
    }

    @ExceptionHandler(value = {CommonRuntimeException.class})
    public ResponseEntity<ExceptionDto> handleCommonRuntimeException(CommonRuntimeException e) {
        if (e.getErrorType() == ErrorType.BAD_REQUEST) {
            ResponseEntity.badRequest().body(new ExceptionDto(new Date(), HttpStatus.BAD_REQUEST, e.getMessage()));
        } else if (e.getErrorType() == ErrorType.PERMISSION_DENIED) {
            ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionDto(new Date(), HttpStatus.FORBIDDEN, "forbidden"));
        } else if (e.getErrorType() == ErrorType.NOT_FOUND) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDto(new Date(), HttpStatus.NOT_FOUND, "not found"));
        }
        return handleException();
    }
}
