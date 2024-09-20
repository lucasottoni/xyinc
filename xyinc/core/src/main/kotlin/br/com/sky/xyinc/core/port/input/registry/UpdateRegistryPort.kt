package br.com.sky.xyinc.core.port.input.registry

import br.com.sky.xyinc.core.domain.Registry

interface UpdateRegistryPort {
    fun update(modelName: String, id: Long, values: List<Registry.RegistryValue>): Registry
}