package br.com.sky.xyinc.core.port.output

import br.com.sky.xyinc.core.domain.FieldDefinition
import br.com.sky.xyinc.core.domain.ModelDefinition

interface CrudModelDefinitionPort {
    fun insert(name: String, fields: List<FieldDefinition>): ModelDefinition
    fun getById(id: Long): ModelDefinition?
    fun getByName(name: String): ModelDefinition?
    fun listAll(): List<ModelDefinition>
    fun delete(id: Long)
}