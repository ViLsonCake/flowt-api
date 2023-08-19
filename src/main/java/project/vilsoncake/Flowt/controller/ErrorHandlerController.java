package project.vilsoncake.Flowt.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.vilsoncake.Flowt.dto.RegistrationValidationDto;
import project.vilsoncake.Flowt.exception.*;

@RestControllerAdvice
@Slf4j
public class ErrorHandlerController {

    @ExceptionHandler
    public ResponseEntity<RegistrationValidationDto> validationException(ValidationException exception) {
        log.warn(exception.getMessage());

        // Create validation dto with needed field
        RegistrationValidationDto validationDto = new RegistrationValidationDto();
        if (exception instanceof UsernameAlreadyExistException) validationDto.setField("username");
        if (exception instanceof EmailAlreadyExistException) validationDto.setField("email");
        if (exception instanceof PasswordsNotMatchException) validationDto.setField("password");
        if (exception instanceof IncorrectCredentialsException) validationDto.setField("login");
        if (exception instanceof TokenNotFoundException) validationDto.setField("token");
        validationDto.setMessage(exception.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(validationDto);
    }

    @ExceptionHandler
    public ResponseEntity<String> minioFileException(MinioFileException exception) {
        log.warn(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> fileExtensionException(InvalidExtensionException exception) {
        log.warn(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}
