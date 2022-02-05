package couch.exhibition.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {

    private final HttpStatus status;
    private final String message;

    @Builder
    ErrorResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ErrorResponse toErrorResponse(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();
        return new ErrorResponse(errorCode.getHttpStatus(), errorCode.getMessage());
    }
}
