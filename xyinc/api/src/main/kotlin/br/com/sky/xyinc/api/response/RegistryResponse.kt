package br.com.sky.xyinc.api.response

import br.com.sky.xyinc.core.domain.Registry

class RegistryResponse {

    companion object {
        fun from(registry: Registry): Map<String, Any> {
            val values =
                listOf(Pair("id", registry.id)) +
                        registry.values.map {
                            Pair(it.fieldName, it.value)
                        }
            return values.toMap()
        }
    }
}