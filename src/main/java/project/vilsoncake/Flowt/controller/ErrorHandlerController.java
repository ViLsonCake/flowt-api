package project.vilsoncake.Flowt.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.vilsoncake.Flowt.exception.ValidationException;

@RestControllerAdvice
@Slf4j
public class ErrorHandlerController {

    @ExceptionHandler
    public ResponseEntity<String> validationException(ValidationException exception) {
        log.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
    }
}
