package com.caterpillar.platform.pc2repasoswagger.mining.application.commands;

/**
 * Command carrying the user-supplied data needed to register equipment.
 *
 * @author Gabriel Yanac Flores
 */
public record CreateMiningEquipmentUnitCommand(
        String model,
        String serialNumber,
        String operationStatus,
        String assignedMineSite,
        double gpsLatitude,
        double gpsLongitude,
        int hoursOfOperation) {
}
