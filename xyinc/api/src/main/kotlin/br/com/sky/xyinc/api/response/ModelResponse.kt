package br.com.sky.xyinc.api.response

import br.com.sky.xyinc.core.domain.FieldDefinition
import br.com.sky.xyinc.core.domain.FieldType
import br.com.sky.xyinc.core.domain.ModelDefinition

data class ModelResponse(
    val id: Long,
    val name: String,
    val fields: List<FieldResponse>
) {
    data class FieldResponse(val name: String, val type: FieldType, val required: Boolean) {
        companion object {
            fun from(domain: FieldDefinition): FieldResponse {
                return FieldResponse(
                    name = domain.name,
                    type = domain.type,
                    required = domain.required
                )
            }
        }
    }

    companion object {
        fun from(domain: ModelDefinition): ModelResponse {
            return ModelResponse(
                id = domain.id,
                name = domain.name,
                fields = domain.fields.map { FieldResponse.from(it) }
            )
        }
    }
}
