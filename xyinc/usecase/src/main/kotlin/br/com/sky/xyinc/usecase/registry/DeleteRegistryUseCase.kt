package br.com.sky.xyinc.usecase.registry

import br.com.sky.xyinc.core.exception.ModelNotFoundException
import br.com.sky.xyinc.core.port.input.registry.DeleteRegistryPort
import br.com.sky.xyinc.core.port.output.CrudModelDefinitionPort
import br.com.sky.xyinc.core.port.output.CrudRegistryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeleteRegistryUseCase(
    private val crudModelDefinitionPort: CrudModelDefinitionPort,
    private val crudRegistryPort: CrudRegistryPort
) : DeleteRegistryPort {

    @Transactional
    override fun delete(modelName: String, id: Long) {
        val model = crudModelDefinitionPort.getByName(modelName) ?: throw ModelNotFoundException(modelName)
        crudRegistryPort.delete(model, id)
    }
}