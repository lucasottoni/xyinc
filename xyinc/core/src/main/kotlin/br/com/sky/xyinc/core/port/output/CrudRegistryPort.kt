package br.com.sky.xyinc.core.port.output

import br.com.sky.xyinc.core.domain.ModelDefinition
import br.com.sky.xyinc.core.domain.Registry

interface CrudRegistryPort {
    fun getById(model: ModelDefinition, id: Long): Registry?
    fun insert(modelDefinition: ModelDefinition, values: List<Registry.RegistryValue>): Registry
    fun update(registry: Registry): Registry
    fun delete(modelDefinition: ModelDefinition, id: Long)
    fun listAll(modelDefinition: ModelDefinition): List<Registry>
    fun countByDefinition(definition: ModelDefinition): Long
}