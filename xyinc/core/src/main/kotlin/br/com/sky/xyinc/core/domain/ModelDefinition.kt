package br.com.sky.xyinc.core.domain

data class ModelDefinition(
    val id: Long,
    val name: String,
    val fields: List<FieldDefinition>
)