package br.com.sky.xyinc.usecase.registry

import br.com.sky.xyinc.core.domain.Registry
import br.com.sky.xyinc.core.exception.ModelNotFoundException
import br.com.sky.xyinc.core.exception.RegistryNotFoundException
import br.com.sky.xyinc.core.port.input.registry.ListRegistryPort
import br.com.sky.xyinc.core.port.output.CrudModelDefinitionPort
import br.com.sky.xyinc.core.port.output.CrudRegistryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class ListRegistryUseCase(
    private val crudModelDefinitionPort: CrudModelDefinitionPort,
    private val crudRegistryPort: CrudRegistryPort
) : ListRegistryPort {

    override fun getById(modelName: String, id: Long): Registry {
        val model = crudModelDefinitionPort.getByName(modelName) ?: throw ModelNotFoundException(modelName)
        return crudRegistryPort.getById(model, id) ?: throw RegistryNotFoundException(model, id)
    }

    override fun listAll(modelName: String): List<Registry> {
        val model = crudModelDefinitionPort.getByName(modelName) ?: throw ModelNotFoundException(modelName)
        return crudRegistryPort.listAll(model)
    }
}