package com.caterpillar.platform.pc2repasoswagger.shared.interfaces.rest.transform;

import com.caterpillar.platform.pc2repasoswagger.shared.application.result.ApplicationError;
import com.caterpillar.platform.pc2repasoswagger.shared.application.result.Result;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.function.Function;

/** Maps application results to success resources or RFC 9457 failure responses. */
public final class ResponseEntityAssembler {

    private ResponseEntityAssembler() { }

    public static <T, R> ResponseEntity<?> toResponseEntityFromResult(
            Result<T, ApplicationError> result,
            Function<T, R> successAssembler,
            HttpStatusCode successStatus) {
        if (result instanceof Result.Success<T, ApplicationError> success) {
            return new ResponseEntity<>(successAssembler.apply(success.value()), successStatus);
        }
        Result.Failure<T, ApplicationError> failure =
                (Result.Failure<T, ApplicationError>) result;
        return ProblemDetailFromApplicationErrorAssembler
                .toResponseFromApplicationError(failure.error());
    }
}
