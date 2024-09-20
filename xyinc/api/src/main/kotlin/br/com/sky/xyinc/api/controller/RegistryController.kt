package br.com.sky.xyinc.api.controller

import br.com.sky.xyinc.api.response.RegistryResponse
import br.com.sky.xyinc.core.domain.Registry
import br.com.sky.xyinc.core.port.input.registry.CreateRegistryPort
import br.com.sky.xyinc.core.port.input.registry.DeleteRegistryPort
import br.com.sky.xyinc.core.port.input.registry.ListRegistryPort
import br.com.sky.xyinc.usecase.registry.CreateRegistryUseCase
import br.com.sky.xyinc.usecase.registry.DeleteRegistryUseCase
import br.com.sky.xyinc.usecase.registry.ListRegistryUseCase
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/registry/{modelName}")
class RegistryController(
    private val listRegistryPort: ListRegistryPort,
    private val createRegistryPort: CreateRegistryPort,
    private val deleteRegistryPort: DeleteRegistryPort
) {

    @GetMapping
    fun listAll(@PathVariable modelName: String): List<Map<String, Any>> {
        return listRegistryPort.listAll(modelName).map { RegistryResponse.from(it) }
    }

    @PostMapping
    fun insert(@PathVariable modelName: String, @RequestBody values: Map<String, Any>): Map<String, Any> {
        return RegistryResponse.from(
            createRegistryPort.create(
                modelName, convertToDomainValues(values)
            )
        )
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("modelName") modelName: String, @PathVariable("id") id: Long) {
        deleteRegistryPort.delete(modelName, id)
    }

    private fun convertToDomainValues(values: Map<String, Any>): List<Registry.RegistryValue> {
        return values.map { Registry.RegistryValue(fieldName = it.key, value = it.value) }
    }
}