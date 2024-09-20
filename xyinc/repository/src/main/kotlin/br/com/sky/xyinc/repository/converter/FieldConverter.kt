package br.com.sky.xyinc.repository.converter

import br.com.sky.xyinc.repository.entity.FieldDefinitionEntity
import jakarta.persistence.AttributeConverter

class FieldConverter : AttributeConverter<List<FieldDefinitionEntity>, String> {

    override fun convertToDatabaseColumn(attribute: List<FieldDefinitionEntity>): String {
        return attribute.objectToJson()
    }

    override fun convertToEntityAttribute(dbData: String?): List<FieldDefinitionEntity> {
        return dbData?.jsonToObject() ?: emptyList()
    }
}