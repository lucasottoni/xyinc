package br.com.sky.xyinc.repository.adapter

import br.com.sky.xyinc.core.domain.ModelDefinition
import br.com.sky.xyinc.core.domain.Registry
import br.com.sky.xyinc.core.port.output.CrudRegistryPort
import br.com.sky.xyinc.repository.jpa.RegistryJdbcRepository
import org.springframework.stereotype.Component

@Component
class CrudRegistryPortAdapter(
    private val registryJdbcRepository: RegistryJdbcRepository
) : CrudRegistryPort {
    override fun getById(model: ModelDefinition, id: Long): Registry? {
        return registryJdbcRepository.findById(model, id)?.toDomain(model)
    }

    override fun insert(modelDefinition: ModelDefinition, values: List<Registry.RegistryValue>): Registry {
        val args = values.associateBy { it.fieldName }.mapValues { it.value.value }
        return registryJdbcRepository.insert(modelDefinition, args).toDomain(modelDefinition)
    }

    override fun update(registry: Registry): Registry {
        val args = registry.values.associateBy { it.fieldName }.mapValues { it.value.value }
        return registryJdbcRepository.update(
            registry.modelDefinition,
            registry.id,
            args
        ).toDomain(registry.modelDefinition)

    }

    override fun delete(modelDefinition: ModelDefinition, id: Long) {
        registryJdbcRepository.delete(modelDefinition, id)
    }

    override fun listAll(modelDefinition: ModelDefinition): List<Registry> {
        return registryJdbcRepository.findAll(modelDefinition)
            .map { it.toDomain(modelDefinition) }
    }

    override fun countByDefinition(definition: ModelDefinition): Long {
        return registryJdbcRepository.countByDefinition(definition)
    }
}