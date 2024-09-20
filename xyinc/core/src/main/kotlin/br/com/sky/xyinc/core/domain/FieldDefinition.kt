package br.com.sky.xyinc.core.domain

data class FieldDefinition(
    val name: String,
    val type: FieldType,
    val required: Boolean
)
