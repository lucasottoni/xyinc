package br.com.sky.xyinc.core.port.input.definition

import br.com.sky.xyinc.core.domain.ModelDefinition

interface ListModelDefinitionPort {
    fun getByName(name: String): ModelDefinition

    fun listAll(): List<ModelDefinition>
}