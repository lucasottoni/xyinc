package br.com.sky.xyinc.core.exception

import br.com.sky.xyinc.core.domain.ModelDefinition

class RegistryNotFoundException(model: ModelDefinition, id: Long) :
    NotFoundException(ErrorCode.REGISTRY_NOT_FOUND, "Registry $id not found for ${model.name}") {

    init {
        addParameter("modelName", model.name)
        addParameter("id", id.toString())
    }
}