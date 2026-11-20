package com.caterpillar.platform.pc2repasoswagger.mining.infrastructure.persistence.jpa.assemblers;

import com.caterpillar.platform.pc2repasoswagger.mining.domain.model.aggregates.MiningEquipmentUnit;
import com.caterpillar.platform.pc2repasoswagger.mining.infrastructure.persistence.jpa.embeddables.GeoCoordinatePersistenceEmbeddable;
import com.caterpillar.platform.pc2repasoswagger.mining.infrastructure.persistence.jpa.entities.MiningEquipmentUnitPersistenceEntity;
import com.caterpillar.platform.pc2repasoswagger.shared.domain.model.valueobjects.GeoCoordinate;

/**
 * Maps between the pure domain aggregate and the JPA persistence model.
 *
 * @author Gabriel Yanac Flores
 */
public final class MiningEquipmentUnitPersistenceAssembler {

    private MiningEquipmentUnitPersistenceAssembler() { }

    public static MiningEquipmentUnit toDomainFromPersistence(
            MiningEquipmentUnitPersistenceEntity entity) {
        return MiningEquipmentUnit.reconstitute(
                entity.getId(),
                entity.getEquipmentUnitId(),
                entity.getModel(),
                entity.getSerialNumber(),
                entity.getOperationStatus(),
                entity.getAssignedMineSite(),
                new GeoCoordinate(
                        entity.getGpsLocation().getLatitude(),
                        entity.getGpsLocation().getLongitude()),
                entity.getHoursOfOperation(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public static MiningEquipmentUnitPersistenceEntity toPersistenceFromDomain(
            MiningEquipmentUnit unit) {
        var entity = new MiningEquipmentUnitPersistenceEntity();
        if (unit.getId() != null) {
            entity.setId(unit.getId());
        }
        entity.setEquipmentUnitId(unit.getEquipmentUnitId());
        entity.setModel(unit.getModel());
        entity.setSerialNumber(unit.getSerialNumber());
        entity.setOperationStatus(unit.getOperationStatus());
        entity.setAssignedMineSite(unit.getAssignedMineSite());
        entity.setGpsLocation(new GeoCoordinatePersistenceEmbeddable(
                unit.getGpsLocation().latitude(), unit.getGpsLocation().longitude()));
        entity.setHoursOfOperation(unit.getHoursOfOperation());
        return entity;
    }
}
