package br.com.sky.xyinc.core.port.output

import br.com.sky.xyinc.core.domain.ModelDefinition

interface RegistryPreparationPort {
    fun create(modelDefinition: ModelDefinition)
    fun drop(modelDefinition: ModelDefinition)
    fun isPrepared(modelDefinition: ModelDefinition): Boolean
}