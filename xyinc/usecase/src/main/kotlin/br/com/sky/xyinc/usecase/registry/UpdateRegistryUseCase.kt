package br.com.sky.xyinc.usecase.registry

import br.com.sky.xyinc.core.domain.Registry
import br.com.sky.xyinc.core.domain.isValid
import br.com.sky.xyinc.core.exception.ModelNotFoundException
import br.com.sky.xyinc.core.exception.RegistryNotFoundException
import br.com.sky.xyinc.core.port.input.registry.UpdateRegistryPort
import br.com.sky.xyinc.core.port.output.CrudModelDefinitionPort
import br.com.sky.xyinc.core.port.output.CrudRegistryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UpdateRegistryUseCase(
    private val crudModelDefinitionPort: CrudModelDefinitionPort,
    private val crudRegistryPort: CrudRegistryPort
) : UpdateRegistryPort {

    @Transactional
    override fun update(modelName: String, id: Long, values: List<Registry.RegistryValue>): Registry {
        val model = crudModelDefinitionPort.getByName(modelName) ?: throw ModelNotFoundException(modelName)
        val registry = crudRegistryPort.getById(model, id) ?: throw RegistryNotFoundException(model, id)

        values.isValid(model.fields)

        return crudRegistryPort.update(
            registry.copy(values = values)
        )
    }
}
