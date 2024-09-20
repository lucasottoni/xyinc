package br.com.sky.xyinc.repository.entity

import br.com.sky.xyinc.core.domain.ModelDefinition
import br.com.sky.xyinc.core.domain.Registry

data class RegistryEntity(
    val id: Long,
    val values: Map<String, Any?>
) {
    fun toDomain(model: ModelDefinition): Registry {
        return Registry(
            id = id,
            values = valuesToDomain(values),
            modelDefinition = model
        )
    }

    private fun valuesToDomain(values: Map<String, Any?>): List<Registry.RegistryValue> {
        return values.filterValues { it != null }.map {
            Registry.RegistryValue(it.key, it.value!!)
        }
    }

    companion object {
        const val ID = "id"
    }

}
