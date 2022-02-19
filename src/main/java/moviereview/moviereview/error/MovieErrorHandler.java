package moviereview.moviereview.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

    @ControllerAdvice
    public class MovieErrorHandler {
        @ExceptionHandler
        public ResponseEntity<ErrorResponse> handleError(Exception ex) {
            ErrorResponse apiError = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }
