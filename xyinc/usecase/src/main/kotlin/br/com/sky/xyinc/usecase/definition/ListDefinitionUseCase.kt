package br.com.sky.xyinc.usecase.definition

import br.com.sky.xyinc.core.domain.ModelDefinition
import br.com.sky.xyinc.core.exception.ModelNotFoundException
import br.com.sky.xyinc.core.port.input.definition.ListModelDefinitionPort
import br.com.sky.xyinc.core.port.output.CrudModelDefinitionPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class ListDefinitionUseCase(
    private val crudModelDefinitionPort: CrudModelDefinitionPort
) : ListModelDefinitionPort {

    override fun getByName(name: String): ModelDefinition {
        return crudModelDefinitionPort.getByName(name) ?: throw ModelNotFoundException(name)
    }

    override fun listAll(): List<ModelDefinition> {
        return crudModelDefinitionPort.listAll()
    }
}