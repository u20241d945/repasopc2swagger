package com.caterpillar.platform.pc2repasoswagger.mining.infrastructure.persistence.jpa.adapters;

import com.caterpillar.platform.pc2repasoswagger.mining.domain.model.aggregates.MiningEquipmentUnit;
import com.caterpillar.platform.pc2repasoswagger.mining.domain.repositories.MiningEquipmentUnitRepository;
import com.caterpillar.platform.pc2repasoswagger.mining.infrastructure.persistence.jpa.assemblers.MiningEquipmentUnitPersistenceAssembler;
import com.caterpillar.platform.pc2repasoswagger.mining.infrastructure.persistence.jpa.repositories.MiningEquipmentUnitPersistenceRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository adapter that keeps Spring Data and encryption details outside the domain.
 *
 * @author Gabriel Yanac Flores
 */
@Repository
public class MiningEquipmentUnitRepositoryImpl implements MiningEquipmentUnitRepository {

    private final MiningEquipmentUnitPersistenceRepository persistenceRepository;

    public MiningEquipmentUnitRepositoryImpl(
            MiningEquipmentUnitPersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public MiningEquipmentUnit save(MiningEquipmentUnit unit) {
        var entity = MiningEquipmentUnitPersistenceAssembler.toPersistenceFromDomain(unit);
        var savedEntity = persistenceRepository.saveAndFlush(entity);
        return MiningEquipmentUnitPersistenceAssembler.toDomainFromPersistence(savedEntity);
    }

    @Override
    public boolean existsBySerialNumberAndAssignedMineSite(
            String serialNumber, String assignedMineSite) {
        return persistenceRepository.findAllByAssignedMineSiteIgnoreCase(assignedMineSite).stream()
                .anyMatch(entity -> entity.getSerialNumber().equals(serialNumber));
    }
}
