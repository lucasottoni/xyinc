package br.com.sky.xyinc.core.port.input.registry

interface DeleteRegistryPort {
    fun delete(modelName: String, id: Long)
}