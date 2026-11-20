package com.caterpillar.platform.pc2repasoswagger.mining.interfaces.rest.transform;

import com.caterpillar.platform.pc2repasoswagger.mining.domain.model.aggregates.MiningEquipmentUnit;
import com.caterpillar.platform.pc2repasoswagger.mining.interfaces.rest.resources.MiningEquipmentUnitResource;

/** Converts the domain aggregate into the public REST response resource. */
public final class MiningEquipmentUnitResourceFromEntityAssembler {

    private MiningEquipmentUnitResourceFromEntityAssembler() { }

    public static MiningEquipmentUnitResource toResourceFromEntity(MiningEquipmentUnit unit) {
        return new MiningEquipmentUnitResource(
                unit.getId(),
                unit.getEquipmentUnitId().toString(),
                unit.getModel(),
                unit.getSerialNumber(),
                unit.getOperationStatus().name(),
                unit.getAssignedMineSite(),
                unit.getGpsLocation().latitude(),
                unit.getGpsLocation().longitude(),
                unit.getHoursOfOperation(),
                unit.getCreatedAt(),
                unit.getUpdatedAt());
    }
}
