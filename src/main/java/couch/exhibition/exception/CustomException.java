package couch.exhibition.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private ErrorCode errorCode;

    public CustomException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
