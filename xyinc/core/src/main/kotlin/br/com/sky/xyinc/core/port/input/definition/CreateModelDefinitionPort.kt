package br.com.sky.xyinc.core.port.input.definition

import br.com.sky.xyinc.core.domain.FieldDefinition
import br.com.sky.xyinc.core.domain.ModelDefinition

interface CreateModelDefinitionPort {
    data class InputCreate(
        val name: String,
        val fields: List<FieldDefinition>
    )

    fun create(input: InputCreate): ModelDefinition
}