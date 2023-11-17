package project.vilsoncake.Flowt.exception;

import lombok.Getter;
import project.vilsoncake.Flowt.dto.ExceptionData;

@Getter
public class OauthRegistrationRequiredException extends RuntimeException {
    private final ExceptionData exceptionData;

    public OauthRegistrationRequiredException(String message, String email, String imageUrl) {
        super(message);
        this.exceptionData = new ExceptionData(email, imageUrl);
    }
}
