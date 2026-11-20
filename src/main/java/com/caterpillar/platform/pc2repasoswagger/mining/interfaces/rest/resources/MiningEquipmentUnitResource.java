package com.caterpillar.platform.pc2repasoswagger.mining.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

/**
 * HTTP response resource for a registered mining equipment unit.
 *
 * @author Gabriel Yanac Flores
 */
@Schema(name = "MiningEquipmentUnitResponse", description = "Registered mining equipment data")
public record MiningEquipmentUnitResource(
        @Schema(example = "1") Long id,
        @Schema(example = "4b27a3f5-8eb2-4bb0-bfea-f3a0927bfe3e") String equipmentUnitId,
        @Schema(example = "CAT 793F") String model,
        @Schema(example = "CAT-793F-0001") String serialNumber,
        @Schema(example = "ACTIVE") String operationStatus,
        @Schema(example = "Antamina") String assignedMineSite,
        @Schema(example = "-9.531") double gpsLatitude,
        @Schema(example = "-77.059") double gpsLongitude,
        @Schema(example = "1250") int hoursOfOperation,
        Instant createdAt,
        Instant updatedAt) {
}
