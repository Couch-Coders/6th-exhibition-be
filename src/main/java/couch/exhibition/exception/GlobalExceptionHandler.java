package couch.exhibition.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        ErrorResponse errorResponse = ErrorResponse.toErrorResponse(e);
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }
}