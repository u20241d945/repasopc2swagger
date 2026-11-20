package com.caterpillar.platform.pc2repasoswagger.shared.application.result;

/**
 * Describes an expected application-layer error that can be translated to HTTP.
 *
 * @param code machine-readable error code
 * @param message human-readable message
 * @param details optional error context
 * @author Gabriel Yanac Flores
 */
public record ApplicationError(String code, String message, String details) {

    public static ApplicationError validationError(String concept, String reason) {
        return new ApplicationError("VALIDATION_ERROR", "Validation failed: " + concept, reason);
    }

    public static ApplicationError conflict(String resource, String reason) {
        return new ApplicationError(
                resource.toUpperCase().replace(' ', '_') + "_CONFLICT",
                "Conflict with " + resource,
                reason);
    }

    public static ApplicationError unexpected(String context) {
        return new ApplicationError(
                "UNEXPECTED_ERROR",
                "Unexpected error in " + context,
                "The operation could not be completed");
    }
}
