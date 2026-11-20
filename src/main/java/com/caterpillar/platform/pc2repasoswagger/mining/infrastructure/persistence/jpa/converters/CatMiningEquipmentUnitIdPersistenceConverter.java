package com.caterpillar.platform.pc2repasoswagger.mining.infrastructure.persistence.jpa.converters;

import com.caterpillar.platform.pc2repasoswagger.mining.domain.model.valueobjects.CatMiningEquipmentUnitId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Stores the business equipment identifier as its canonical UUID string.
 *
 * @author Gabriel Yanac Flores
 */
@Converter
public class CatMiningEquipmentUnitIdPersistenceConverter
        implements AttributeConverter<CatMiningEquipmentUnitId, String> {

    @Override
    public String convertToDatabaseColumn(CatMiningEquipmentUnitId attribute) {
        return attribute == null ? null : attribute.toString();
    }

    @Override
    public CatMiningEquipmentUnitId convertToEntityAttribute(String value) {
        return value == null ? null : CatMiningEquipmentUnitId.from(value);
    }
}
