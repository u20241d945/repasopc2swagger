package com.caterpillar.platform.pc2repasoswagger.mining.interfaces.rest.transform;

import com.caterpillar.platform.pc2repasoswagger.mining.application.commands.CreateMiningEquipmentUnitCommand;
import com.caterpillar.platform.pc2repasoswagger.mining.interfaces.rest.resources.CreateMiningEquipmentUnitResource;

/** Converts the create request resource into an application command. */
public final class CreateMiningEquipmentUnitCommandFromResourceAssembler {

    private CreateMiningEquipmentUnitCommandFromResourceAssembler() { }

    public static CreateMiningEquipmentUnitCommand toCommandFromResource(
            CreateMiningEquipmentUnitResource resource) {
        return new CreateMiningEquipmentUnitCommand(
                resource.model(),
                resource.serialNumber(),
                resource.operationStatus(),
                resource.assignedMineSite(),
                resource.gpsLatitude(),
                resource.gpsLongitude(),
                resource.hoursOfOperation());
    }
}
