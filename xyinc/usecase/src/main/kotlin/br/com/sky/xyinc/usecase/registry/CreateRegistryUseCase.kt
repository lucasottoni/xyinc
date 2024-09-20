package br.com.sky.xyinc.usecase.registry

import br.com.sky.xyinc.core.domain.Registry
import br.com.sky.xyinc.core.domain.isValid
import br.com.sky.xyinc.core.exception.ModelNotFoundException
import br.com.sky.xyinc.core.port.input.registry.CreateRegistryPort
import br.com.sky.xyinc.core.port.output.CrudModelDefinitionPort
import br.com.sky.xyinc.core.port.output.CrudRegistryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateRegistryUseCase(
    private val crudModelDefinitionPort: CrudModelDefinitionPort,
    private val crudRegistryPort: CrudRegistryPort
) : CreateRegistryPort {

    @Transactional
    override fun create(modelName: String, values: List<Registry.RegistryValue>): Registry {
        val model = crudModelDefinitionPort.getByName(modelName) ?: throw ModelNotFoundException(modelName)

        values.isValid(model.fields)

        return crudRegistryPort.insert(
            modelDefinition = model,
            values = values
        )
    }
}