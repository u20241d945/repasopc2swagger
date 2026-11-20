package com.caterpillar.platform.pc2repasoswagger.mining.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

/**
 * HTTP request resource for registering a mining equipment unit.
 *
 * @author Gabriel Yanac Flores
 */
@Schema(name = "CreateMiningEquipmentUnitRequest", description = "User-supplied mining equipment data")
public record CreateMiningEquipmentUnitResource(
        @NotBlank(message = "Model is required")
        @Size(max = 255, message = "Model must not exceed 255 characters")
        @Schema(example = "CAT 793F")
        String model,

        @NotBlank(message = "Serial number is required")
        @Size(max = 255, message = "Serial number must not exceed 255 characters")
        @Schema(example = "CAT-793F-0001")
        String serialNumber,

        @NotBlank(message = "Operation status is required")
        @Schema(description = "ACTIVE, IN_MAINTENANCE, or INACTIVE", example = "ACTIVE")
        String operationStatus,

        @NotBlank(message = "Assigned mine site is required")
        @Size(max = 255, message = "Assigned mine site must not exceed 255 characters")
        @Schema(example = "Antamina")
        String assignedMineSite,

        @NotNull(message = "GPS latitude is required")
        @DecimalMin(value = "-90.0", message = "GPS latitude must be at least -90")
        @DecimalMax(value = "90.0", message = "GPS latitude must not exceed 90")
        @Schema(example = "-9.531")
        Double gpsLatitude,

        @NotNull(message = "GPS longitude is required")
        @DecimalMin(value = "-180.0", message = "GPS longitude must be at least -180")
        @DecimalMax(value = "180.0", message = "GPS longitude must not exceed 180")
        @Schema(example = "-77.059")
        Double gpsLongitude,

        @NotNull(message = "Hours of operation is required")
        @PositiveOrZero(message = "Hours of operation must be zero or greater")
        @Schema(example = "1250")
        Integer hoursOfOperation) {
}
