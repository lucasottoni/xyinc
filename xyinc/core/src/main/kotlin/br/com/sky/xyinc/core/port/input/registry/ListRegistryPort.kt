package br.com.sky.xyinc.core.port.input.registry

import br.com.sky.xyinc.core.domain.Registry

interface ListRegistryPort {
    fun getById(modelName: String, id: Long): Registry
    fun listAll(modelName: String): List<Registry>
}