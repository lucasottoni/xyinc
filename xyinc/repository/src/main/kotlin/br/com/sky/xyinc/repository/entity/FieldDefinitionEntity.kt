package br.com.sky.xyinc.repository.entity

import br.com.sky.xyinc.core.domain.FieldDefinition
import br.com.sky.xyinc.core.domain.FieldType

data class FieldDefinitionEntity(
    val name: String,
    val type: FieldType,
    val required: Boolean
) {
    fun toDomain(): FieldDefinition {
        return FieldDefinition(name, type, required)
    }
}
