package br.com.sky.xyinc.core.domain

import br.com.sky.xyinc.core.exception.RegistryInvalidFieldsException

data class Registry(
    val id: Long,
    val values: List<RegistryValue>,
    val modelDefinition: ModelDefinition
) {
    data class RegistryValue(
        val fieldName: String,
        val value: Any
    )
}

fun List<Registry.RegistryValue>.isValid(fields: List<FieldDefinition>) {
    val errors = mutableListOf<RegistryFieldError>()
    fields.forEach { field ->
        this.validate(field)?.also { errors.add(it) }
    }
    if (errors.isNotEmpty()) throw RegistryInvalidFieldsException(errors)
}

private fun List<Registry.RegistryValue>.validate(field: FieldDefinition): RegistryFieldError? {
    val valueItem = this.firstOrNull { v -> v.fieldName == field.name }
    if (valueItem == null && field.required) return RegistryFieldError(
        field,
        RegistryFieldError.ErrorType.REQUIRED_FIELD,
        null
    )

    if (valueItem != null && !field.type.accepts(valueItem.value)) return RegistryFieldError(
        field,
        RegistryFieldError.ErrorType.VALUE_TYPE_MISMATCH,
        valueItem.value
    )

    return null
}
