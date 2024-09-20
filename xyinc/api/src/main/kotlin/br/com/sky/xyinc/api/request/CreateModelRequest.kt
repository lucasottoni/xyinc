package br.com.sky.xyinc.api.request

import br.com.sky.xyinc.core.domain.FieldDefinition
import br.com.sky.xyinc.core.domain.FieldType

data class CreateModelRequest(
    val name: String,
    val fields: List<FieldRequest>
) {
    data class FieldRequest(
        val name: String,
        val type: FieldType,
        val required: Boolean
    )
}

fun CreateModelRequest.FieldRequest.toDomain(): FieldDefinition {
    return FieldDefinition(
        name = this.name,
        type = this.type,
        required = this.required
    )
}
