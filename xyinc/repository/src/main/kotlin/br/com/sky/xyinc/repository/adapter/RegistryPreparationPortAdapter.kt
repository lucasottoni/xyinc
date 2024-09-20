package br.com.sky.xyinc.repository.adapter

import br.com.sky.xyinc.core.domain.ModelDefinition
import br.com.sky.xyinc.core.port.output.RegistryPreparationPort
import br.com.sky.xyinc.repository.jpa.RegistryJdbcRepository
import org.springframework.stereotype.Component

@Component
class RegistryPreparationPortAdapter(
    private val registryJdbcRepository: RegistryJdbcRepository
) : RegistryPreparationPort {
    override fun create(modelDefinition: ModelDefinition) {
        registryJdbcRepository.createTable(modelDefinition)
    }

    override fun drop(modelDefinition: ModelDefinition) {
        TODO("Not yet implemented")
    }

    override fun isPrepared(modelDefinition: ModelDefinition): Boolean {
        return registryJdbcRepository.checkTable(modelDefinition)
    }
}