package project.vilsoncake.Flowt.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import project.vilsoncake.Flowt.dto.ExceptionData;
import project.vilsoncake.Flowt.dto.RegistrationValidationDto;
import project.vilsoncake.Flowt.exception.*;

import java.io.IOException;
import java.util.Map;

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
        if (exception instanceof InvalidPasswordCodeException) validationDto.setField("code");
        validationDto.setMessage(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(validationDto);
    }

    @ExceptionHandler
    public ResponseEntity<String> minioFileException(MinioFileException exception) {
        log.warn(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> fileExtensionException(InvalidExtensionException exception) {
        log.warn(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> accountVerifiedException(AccountAlreadyVerifiedException exception) {
        log.warn(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> hibernateValidationError(ConstraintViolationException exception) {
        String convertErrorMessages = exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .toString();

        log.warn(convertErrorMessages.substring(1, convertErrorMessages.length() - 1));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", convertErrorMessages.substring(1, convertErrorMessages.length() - 1)));
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> webclientException(WebClientResponseException exception) {
        log.warn(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionData> oauthRegistrationRequiredException(OauthRegistrationRequiredException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getExceptionData());
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> IOException(IOException e) {
        log.warn(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
    }
}
