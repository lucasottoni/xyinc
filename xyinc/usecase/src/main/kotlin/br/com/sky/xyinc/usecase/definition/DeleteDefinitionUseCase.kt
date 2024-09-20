package br.com.sky.xyinc.usecase.definition

import br.com.sky.xyinc.core.exception.ModelNotEmptyException
import br.com.sky.xyinc.core.port.input.definition.DeleteModelDefinitionPort
import br.com.sky.xyinc.core.port.output.CrudModelDefinitionPort
import br.com.sky.xyinc.core.port.output.CrudRegistryPort
import br.com.sky.xyinc.core.port.output.RegistryPreparationPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeleteDefinitionUseCase(
    private val crudModelDefinitionPort: CrudModelDefinitionPort,
    private val crudRegistryPort: CrudRegistryPort,
    private val registryPreparationPort: RegistryPreparationPort
) : DeleteModelDefinitionPort {

    @Transactional
    override fun delete(modelName: String) {
        val definition = crudModelDefinitionPort.getByName(modelName) ?: return

        if (registryPreparationPort.isPrepared(modelDefinition = definition)) {
            val qtd = crudRegistryPort.countByDefinition(definition)

            if (qtd > 0) {
                throw ModelNotEmptyException(modelName = definition.name, qtdRegistry = qtd)
            }

            registryPreparationPort.drop(definition)
        }

        crudModelDefinitionPort.delete(definition.id)
    }
}