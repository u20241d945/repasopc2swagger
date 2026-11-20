package com.caterpillar.platform.pc2repasoswagger.shared.interfaces.rest.transform;

import com.caterpillar.platform.pc2repasoswagger.shared.application.result.ApplicationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Locale;

/**
 * Maps application errors to RFC 9457 problem details.
 *
 * @author Gabriel Yanac Flores
 */
public final class ProblemDetailFromApplicationErrorAssembler {

    private ProblemDetailFromApplicationErrorAssembler() { }

    public static ProblemDetail toProblemDetailFromApplicationError(ApplicationError error) {
        HttpStatus status = toStatusFromErrorCode(error.code());
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, error.message());
        problem.setTitle(toTitle(status));
        problem.setType(URI.create("/api/v1/errors/" + error.code()
                .toLowerCase(Locale.ROOT).replace('_', '-')));
        problem.setProperty("code", error.code());
        if (error.details() != null && !error.details().isBlank()) {
            problem.setProperty("details", error.details());
        }
        return problem;
    }

    public static ResponseEntity<ProblemDetail> toResponseFromApplicationError(ApplicationError error) {
        ProblemDetail problem = toProblemDetailFromApplicationError(error);
        return ResponseEntity.status(problem.getStatus()).body(problem);
    }

    public static HttpStatus toStatusFromErrorCode(String code) {
        if ("VALIDATION_ERROR".equals(code)) {
            return HttpStatus.BAD_REQUEST;
        }
        if (code.endsWith("_CONFLICT")) {
            return HttpStatus.CONFLICT;
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private static String toTitle(HttpStatus status) {
        return switch (status) {
            case BAD_REQUEST -> "Invalid Request";
            case CONFLICT -> "Resource Conflict";
            default -> "Internal Server Error";
        };
    }
}
