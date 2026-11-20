package com.caterpillar.platform.pc2repasoswagger.mining.domain.model.valueobjects;

/**
 * Supported operational states for mining equipment.
 *
 * @author Gabriel Yanac Flores
 */
public enum OperationStatus {
    ACTIVE,
    IN_MAINTENANCE,
    INACTIVE;

    public static OperationStatus from(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Operation status is required");
        }
        try {
            return OperationStatus.valueOf(value.trim());
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(
                    "Operation status must be ACTIVE, IN_MAINTENANCE, or INACTIVE");
        }
    }
}
