package com.caterpillar.platform.pc2repasoswagger.mining.application.commandservices;

import com.caterpillar.platform.pc2repasoswagger.mining.application.commands.CreateMiningEquipmentUnitCommand;
import com.caterpillar.platform.pc2repasoswagger.mining.domain.model.aggregates.MiningEquipmentUnit;
import com.caterpillar.platform.pc2repasoswagger.shared.application.result.ApplicationError;
import com.caterpillar.platform.pc2repasoswagger.shared.application.result.Result;

/**
 * Application port for mining equipment write use cases.
 *
 * @author Gabriel Yanac Flores
 */
public interface MiningEquipmentUnitCommandService {

    Result<MiningEquipmentUnit, ApplicationError> handle(CreateMiningEquipmentUnitCommand command);
}
