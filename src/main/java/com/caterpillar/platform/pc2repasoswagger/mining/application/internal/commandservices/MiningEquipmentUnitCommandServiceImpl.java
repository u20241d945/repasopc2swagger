package com.caterpillar.platform.pc2repasoswagger.mining.application.internal.commandservices;

import com.caterpillar.platform.pc2repasoswagger.mining.application.commands.CreateMiningEquipmentUnitCommand;
import com.caterpillar.platform.pc2repasoswagger.mining.application.commandservices.MiningEquipmentUnitCommandService;
import com.caterpillar.platform.pc2repasoswagger.mining.domain.model.aggregates.MiningEquipmentUnit;
import com.caterpillar.platform.pc2repasoswagger.mining.domain.model.valueobjects.OperationStatus;
import com.caterpillar.platform.pc2repasoswagger.mining.domain.repositories.MiningEquipmentUnitRepository;
import com.caterpillar.platform.pc2repasoswagger.shared.application.result.ApplicationError;
import com.caterpillar.platform.pc2repasoswagger.shared.application.result.Result;
import com.caterpillar.platform.pc2repasoswagger.shared.domain.model.valueobjects.GeoCoordinate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Executes the register-equipment command and enforces cross-aggregate rules.
 *
 * @author Gabriel Yanac Flores
 */
@Service
public class MiningEquipmentUnitCommandServiceImpl implements MiningEquipmentUnitCommandService {

    private final MiningEquipmentUnitRepository repository;

    public MiningEquipmentUnitCommandServiceImpl(MiningEquipmentUnitRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Result<MiningEquipmentUnit, ApplicationError> handle(CreateMiningEquipmentUnitCommand command) {
        final OperationStatus operationStatus;
        final GeoCoordinate gpsLocation;
        final MiningEquipmentUnit unit;

        try {
            operationStatus = OperationStatus.from(command.operationStatus());
            gpsLocation = new GeoCoordinate(command.gpsLatitude(), command.gpsLongitude());
            unit = MiningEquipmentUnit.create(
                    command.model(),
                    command.serialNumber(),
                    operationStatus,
                    command.assignedMineSite(),
                    gpsLocation,
                    command.hoursOfOperation());
        } catch (IllegalArgumentException | NullPointerException exception) {
            return Result.failure(ApplicationError.validationError("equipment unit", exception.getMessage()));
        }

        if (repository.existsBySerialNumberAndAssignedMineSite(
                unit.getSerialNumber(), unit.getAssignedMineSite())) {
            return Result.failure(ApplicationError.conflict(
                    "mining equipment unit",
                    "Serial number '%s' is already registered at mine site '%s'"
                            .formatted(unit.getSerialNumber(), unit.getAssignedMineSite())));
        }

        try {
            return Result.success(repository.save(unit));
        } catch (RuntimeException exception) {
            return Result.failure(ApplicationError.unexpected("create-mining-equipment-unit"));
        }
    }
}
