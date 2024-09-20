package br.com.sky.xyinc.api.controller

import br.com.sky.xyinc.api.request.CreateModelRequest
import br.com.sky.xyinc.api.request.toDomain
import br.com.sky.xyinc.api.response.ModelResponse
import br.com.sky.xyinc.core.port.input.definition.CreateModelDefinitionPort
import br.com.sky.xyinc.core.port.input.definition.DeleteModelDefinitionPort
import br.com.sky.xyinc.core.port.input.definition.ListModelDefinitionPort
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/model")
class ModelController(
    private val createModelDefinitionPort: CreateModelDefinitionPort,
    private val deleteModelDefinitionPort: DeleteModelDefinitionPort,
    private val listModelDefinitionPort: ListModelDefinitionPort
) {

    @PostMapping
    fun createModel(@Validated @RequestBody request: CreateModelRequest): ModelResponse {
        return ModelResponse.from(createModelDefinitionPort.create(
            input = CreateModelDefinitionPort.InputCreate(
                name = request.name,
                fields = request.fields.map { it.toDomain() }
            )
        ))
    }

    @DeleteMapping("/{modelName}")
    fun deleteModel(@PathVariable modelName: String) {
        deleteModelDefinitionPort.delete(modelName)
    }

    @GetMapping
    fun listAllModel(): List<ModelResponse> {
        return listModelDefinitionPort.listAll().map {
            ModelResponse.from(it)
        }
    }
}