package com.caterpillar.platform.pc2repasoswagger.mining.domain.model.aggregates;

import com.caterpillar.platform.pc2repasoswagger.mining.domain.model.valueobjects.CatMiningEquipmentUnitId;
import com.caterpillar.platform.pc2repasoswagger.mining.domain.model.valueobjects.OperationStatus;
import com.caterpillar.platform.pc2repasoswagger.shared.domain.model.valueobjects.GeoCoordinate;

import java.time.Instant;
import java.util.Objects;

/**
 * Aggregate root for a Caterpillar mining equipment unit.
 *
 * <p>The aggregate contains only business state and has no JPA or HTTP dependencies.</p>
 *
 * @author Gabriel Yanac Flores
 */
public final class MiningEquipmentUnit {

    private final Long id;
    private final CatMiningEquipmentUnitId equipmentUnitId;
    private final String model;
    private final String serialNumber;
    private final OperationStatus operationStatus;
    private final String assignedMineSite;
    private final GeoCoordinate gpsLocation;
    private final int hoursOfOperation;
    private final Instant createdAt;
    private final Instant updatedAt;

    private MiningEquipmentUnit(
            Long id,
            CatMiningEquipmentUnitId equipmentUnitId,
            String model,
            String serialNumber,
            OperationStatus operationStatus,
            String assignedMineSite,
            GeoCoordinate gpsLocation,
            int hoursOfOperation,
            Instant createdAt,
            Instant updatedAt) {
        this.id = id;
        this.equipmentUnitId = Objects.requireNonNull(equipmentUnitId, "Equipment unit ID is required");
        this.model = requireText(model, "Model");
        this.serialNumber = requireText(serialNumber, "Serial number");
        this.operationStatus = Objects.requireNonNull(operationStatus, "Operation status is required");
        this.assignedMineSite = requireText(assignedMineSite, "Assigned mine site");
        this.gpsLocation = Objects.requireNonNull(gpsLocation, "GPS location is required");
        if (hoursOfOperation < 0) {
            throw new IllegalArgumentException("Hours of operation must be zero or greater");
        }
        this.hoursOfOperation = hoursOfOperation;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /** Creates a new aggregate and generates its business identifier. */
    public static MiningEquipmentUnit create(
            String model,
            String serialNumber,
            OperationStatus operationStatus,
            String assignedMineSite,
            GeoCoordinate gpsLocation,
            int hoursOfOperation) {
        return new MiningEquipmentUnit(
                null,
                CatMiningEquipmentUnitId.generate(),
                model,
                serialNumber,
                operationStatus,
                assignedMineSite,
                gpsLocation,
                hoursOfOperation,
                null,
                null);
    }

    /** Rebuilds an aggregate that was read from persistence. */
    public static MiningEquipmentUnit reconstitute(
            Long id,
            CatMiningEquipmentUnitId equipmentUnitId,
            String model,
            String serialNumber,
            OperationStatus operationStatus,
            String assignedMineSite,
            GeoCoordinate gpsLocation,
            int hoursOfOperation,
            Instant createdAt,
            Instant updatedAt) {
        return new MiningEquipmentUnit(
                id, equipmentUnitId, model, serialNumber, operationStatus, assignedMineSite,
                gpsLocation, hoursOfOperation, createdAt, updatedAt);
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " is required");
        }
        return value.trim();
    }

    public Long getId() { return id; }
    public CatMiningEquipmentUnitId getEquipmentUnitId() { return equipmentUnitId; }
    public String getModel() { return model; }
    public String getSerialNumber() { return serialNumber; }
    public OperationStatus getOperationStatus() { return operationStatus; }
    public String getAssignedMineSite() { return assignedMineSite; }
    public GeoCoordinate getGpsLocation() { return gpsLocation; }
    public int getHoursOfOperation() { return hoursOfOperation; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
