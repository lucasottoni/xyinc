package br.com.sky.xyinc.core.exception

abstract class BusinessException(
    val errorCode: ErrorCode,
    val errorMessage: String,
) : RuntimeException(errorMessage) {
    private val parameters = mutableMapOf<String, String>()

    protected fun addParameter(key: String, value: String) {
        parameters[key] = value
    }

    fun getParameters() = parameters.toMap()
}