package br.com.sky.xyinc.repository.adapter

import br.com.sky.xyinc.core.domain.FieldDefinition
import br.com.sky.xyinc.core.domain.ModelDefinition
import br.com.sky.xyinc.core.port.output.CrudModelDefinitionPort
import br.com.sky.xyinc.repository.jpa.ModelDefinitionRepository
import br.com.sky.xyinc.repository.entity.ModelDefinitionEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class CrudModelDefinitionPortAdapter(
    private val modelDefinitionRepository: ModelDefinitionRepository
) : CrudModelDefinitionPort {
    override fun insert(name: String, fields: List<FieldDefinition>): ModelDefinition {
        val entity = ModelDefinitionEntity(
            name = name,
            fields = ModelDefinitionEntity.toEntity(fields)
        )
        return modelDefinitionRepository.save(entity).toDomain()
    }

    override fun getById(id: Long): ModelDefinition? {
        return modelDefinitionRepository.findByIdOrNull(id)?.toDomain()
    }

    override fun getByName(name: String): ModelDefinition? {
        return modelDefinitionRepository.findByName(name)?.toDomain()
    }

    override fun listAll(): List<ModelDefinition> {
        return modelDefinitionRepository.findAll().map { it.toDomain() }
    }

    override fun delete(id: Long) {
        modelDefinitionRepository.deleteById(id)
    }
}