package com.accesoriosdm.inventory.exception.handler;

import com.accesoriosdm.inventory.exception.DomainException;
import com.accesoriosdm.inventory.exception.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomain(DomainException ex, HttpServletRequest req) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(build(ex.getStatus().value(), ex.getErrorCode(), ex.getMessage(), req, List.of()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        List<Object> details = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> (Object) Map.of(
                        "field", fe.getField(),
                        "rejectedValue", fe.getRejectedValue() != null ? fe.getRejectedValue().toString() : "",
                        "message", fe.getDefaultMessage() != null ? fe.getDefaultMessage() : ""))
                .toList();

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(build(422, "VALIDATION_ERROR", "Los datos enviados no son válidos.", req, details));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception ex, HttpServletRequest req) {
        log.error("Unexpected error on {}: {}", req.getRequestURI(), ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(build(500, "INTERNAL_SERVER_ERROR",
                        "Ocurrió un error interno. Por favor intente de nuevo.", req, List.of()));
    }

    private ErrorResponse build(int status, String error, String message,
                                 HttpServletRequest req, List<Object> details) {
        String traceId = req.getHeader("X-Trace-Id");
        if (traceId == null || traceId.isBlank()) {
            traceId = UUID.randomUUID().toString();
        }
        return new ErrorResponse(
                status,
                error,
                message,
                req.getRequestURI(),
                ZonedDateTime.now(ZoneOffset.UTC).toString(),
                traceId,
                details
        );
    }
}
