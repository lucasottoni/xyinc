package br.com.sky.xyinc.core.domain

data class RegistryFieldError(val field: FieldDefinition, val errorType: ErrorType, val value: Any?) {
    enum class ErrorType {
        REQUIRED_FIELD,
        VALUE_TYPE_MISMATCH
    }
}
