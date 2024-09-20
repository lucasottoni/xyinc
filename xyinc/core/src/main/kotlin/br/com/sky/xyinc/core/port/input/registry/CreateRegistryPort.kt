package br.com.sky.xyinc.core.port.input.registry

import br.com.sky.xyinc.core.domain.Registry

interface CreateRegistryPort {
    fun create(modelName: String, values: List<Registry.RegistryValue>): Registry
}