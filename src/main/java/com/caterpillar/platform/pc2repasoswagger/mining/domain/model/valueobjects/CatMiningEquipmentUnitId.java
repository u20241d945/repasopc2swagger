package com.caterpillar.platform.pc2repasoswagger.mining.domain.model.valueobjects;

import java.util.Objects;
import java.util.UUID;

/**
 * Stable business identifier assigned to a mining equipment unit.
 *
 * @param value UUID value
 * @author Gabriel Yanac Flores
 */
public record CatMiningEquipmentUnitId(UUID value) {

    public CatMiningEquipmentUnitId {
        Objects.requireNonNull(value, "Equipment unit ID cannot be null");
    }

    public static CatMiningEquipmentUnitId generate() {
        return new CatMiningEquipmentUnitId(UUID.randomUUID());
    }

    public static CatMiningEquipmentUnitId from(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Equipment unit ID cannot be blank");
        }
        return new CatMiningEquipmentUnitId(UUID.fromString(value));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
