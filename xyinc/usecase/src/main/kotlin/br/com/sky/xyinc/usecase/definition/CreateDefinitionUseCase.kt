package br.com.sky.xyinc.usecase.definition

import br.com.sky.xyinc.core.domain.ModelDefinition
import br.com.sky.xyinc.core.exception.ModelAlreadyExistsException
import br.com.sky.xyinc.core.exception.ModelNotPreparedException
import br.com.sky.xyinc.core.port.input.definition.CreateModelDefinitionPort
import br.com.sky.xyinc.core.port.output.CrudModelDefinitionPort
import br.com.sky.xyinc.core.port.output.RegistryPreparationPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateDefinitionUseCase(
    private val crudModelDefinitionPort: CrudModelDefinitionPort,
    private val registryPreparationPort: RegistryPreparationPort
) : CreateModelDefinitionPort {

    @Transactional
    override fun create(input: CreateModelDefinitionPort.InputCreate): ModelDefinition {
        crudModelDefinitionPort.getByName(input.name)?.run {
            throw ModelAlreadyExistsException(name)
        }

        val definition = crudModelDefinitionPort.insert(
            name = input.name,
            fields = input.fields
        )

        if (registryPreparationPort.isPrepared(definition))
            throw ModelNotPreparedException(definition.name)

        registryPreparationPort.create(definition)

        return definition
    }
}