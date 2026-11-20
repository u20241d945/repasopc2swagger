package com.caterpillar.platform.pc2repasoswagger.shared.application.result;

import java.util.function.Function;

/**
 * Represents either a successful value or an expected failure.
 *
 * @param <T> success value type
 * @param <E> failure type
 * @author Gabriel Yanac Flores
 */
public sealed interface Result<T, E> {

    record Success<T, E>(T value) implements Result<T, E> { }

    record Failure<T, E>(E error) implements Result<T, E> { }

    static <T, E> Result<T, E> success(T value) {
        return new Success<>(value);
    }

    static <T, E> Result<T, E> failure(E error) {
        return new Failure<>(error);
    }

    default <R> Result<R, E> map(Function<T, R> mapper) {
        if (this instanceof Success<T, E> success) {
            return Result.success(mapper.apply(success.value()));
        }
        Failure<T, E> failure = (Failure<T, E>) this;
        return Result.failure(failure.error());
    }
}
