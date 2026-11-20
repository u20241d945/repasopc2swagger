package com.caterpillar.platform.pc2repasoswagger.shared.interfaces.rest;

import com.caterpillar.platform.pc2repasoswagger.shared.application.result.ApplicationError;
import com.caterpillar.platform.pc2repasoswagger.shared.interfaces.rest.transform.ProblemDetailFromApplicationErrorAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.LinkedHashMap;

/**
 * Translates request and unexpected exceptions into consistent English problem details.
 *
 * @author Gabriel Yanac Flores
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidation(MethodArgumentNotValidException exception) {
        var invalidParams = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> {
                    var parameter = new LinkedHashMap<String, String>();
                    parameter.put("field", error.getField());
                    parameter.put("message", error.getDefaultMessage() == null
                            ? "Invalid value" : error.getDefaultMessage());
                    return parameter;
                })
                .toList();
        var error = ApplicationError.validationError(
                "request body", "One or more request fields are invalid");
        ProblemDetail problem = ProblemDetailFromApplicationErrorAssembler
                .toProblemDetailFromApplicationError(error);
        problem.setProperty("invalidParams", invalidParams);
        return ResponseEntity.badRequest().body(problem);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> handleUnreadableRequest(HttpMessageNotReadableException exception) {
        var error = ApplicationError.validationError(
                "request body", "The JSON body is malformed or contains unsupported fields");
        return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> handleIllegalArgument(IllegalArgumentException exception) {
        var error = ApplicationError.validationError("request", exception.getMessage());
        return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleUnexpected(Exception exception) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        problem.setTitle("Internal Server Error");
        problem.setType(URI.create("/api/v1/errors/unexpected-error"));
        problem.setProperty("code", "UNEXPECTED_ERROR");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problem);
    }
}
