package com.caterpillar.platform.pc2repasoswagger.mining.infrastructure.persistence.jpa.entities;

import com.caterpillar.platform.pc2repasoswagger.mining.domain.model.valueobjects.CatMiningEquipmentUnitId;
import com.caterpillar.platform.pc2repasoswagger.mining.domain.model.valueobjects.OperationStatus;
import com.caterpillar.platform.pc2repasoswagger.mining.infrastructure.persistence.jpa.converters.CatMiningEquipmentUnitIdPersistenceConverter;
import com.caterpillar.platform.pc2repasoswagger.mining.infrastructure.persistence.jpa.converters.SerialNumberEncryptionConverter;
import com.caterpillar.platform.pc2repasoswagger.mining.infrastructure.persistence.jpa.embeddables.GeoCoordinatePersistenceEmbeddable;
import com.caterpillar.platform.pc2repasoswagger.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

/**
 * JPA persistence model for the mining equipment aggregate.
 *
 * @author Gabriel Yanac Flores
 */
@Entity
@Table(name = "mining_equipment_unit")
public class MiningEquipmentUnitPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Convert(converter = CatMiningEquipmentUnitIdPersistenceConverter.class)
    @Column(nullable = false, unique = true, length = 36)
    private CatMiningEquipmentUnitId equipmentUnitId;

    @Column(nullable = false)
    private String model;

    @Convert(converter = SerialNumberEncryptionConverter.class)
    @Column(nullable = false, length = 512)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private OperationStatus operationStatus;

    @Column(nullable = false)
    private String assignedMineSite;

    @Embedded
    private GeoCoordinatePersistenceEmbeddable gpsLocation;

    @Column(nullable = false)
    private int hoursOfOperation;

    public MiningEquipmentUnitPersistenceEntity() { }

    public CatMiningEquipmentUnitId getEquipmentUnitId() { return equipmentUnitId; }
    public void setEquipmentUnitId(CatMiningEquipmentUnitId equipmentUnitId) { this.equipmentUnitId = equipmentUnitId; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
    public OperationStatus getOperationStatus() { return operationStatus; }
    public void setOperationStatus(OperationStatus operationStatus) { this.operationStatus = operationStatus; }
    public String getAssignedMineSite() { return assignedMineSite; }
    public void setAssignedMineSite(String assignedMineSite) { this.assignedMineSite = assignedMineSite; }
    public GeoCoordinatePersistenceEmbeddable getGpsLocation() { return gpsLocation; }
    public void setGpsLocation(GeoCoordinatePersistenceEmbeddable gpsLocation) { this.gpsLocation = gpsLocation; }
    public int getHoursOfOperation() { return hoursOfOperation; }
    public void setHoursOfOperation(int hoursOfOperation) { this.hoursOfOperation = hoursOfOperation; }
}
