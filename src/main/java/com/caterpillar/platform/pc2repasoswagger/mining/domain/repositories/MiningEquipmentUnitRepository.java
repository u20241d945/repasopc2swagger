package com.caterpillar.platform.pc2repasoswagger.mining.domain.repositories;

import com.caterpillar.platform.pc2repasoswagger.mining.domain.model.aggregates.MiningEquipmentUnit;

/**
 * Domain port for mining equipment persistence.
 *
 * @author Gabriel Yanac Flores
 */
public interface MiningEquipmentUnitRepository {

    MiningEquipmentUnit save(MiningEquipmentUnit unit);

    boolean existsBySerialNumberAndAssignedMineSite(String serialNumber, String assignedMineSite);
}
