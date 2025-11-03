package net.ayad.firstcicdpipeline.exception;

import jakarta.servlet.http.HttpServletRequest;
import net.ayad.firstcicdpipeline.dto.ApiErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleProductNotFoundException(ProductNotFoundException ex, HttpServletRequest request) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                LocalDateTime.now(),
                "PRODUCT_NOT_FOUND",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(NOT_FOUND).body(apiErrorResponse);
    }
}
