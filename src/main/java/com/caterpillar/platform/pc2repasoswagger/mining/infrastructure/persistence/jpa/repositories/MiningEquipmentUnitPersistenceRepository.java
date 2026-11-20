package com.caterpillar.platform.pc2repasoswagger.mining.infrastructure.persistence.jpa.repositories;

import com.caterpillar.platform.pc2repasoswagger.mining.infrastructure.persistence.jpa.entities.MiningEquipmentUnitPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/** Spring Data repository used only by the infrastructure adapter. */
public interface MiningEquipmentUnitPersistenceRepository
        extends JpaRepository<MiningEquipmentUnitPersistenceEntity, Long> {

    List<MiningEquipmentUnitPersistenceEntity> findAllByAssignedMineSiteIgnoreCase(String assignedMineSite);
}
