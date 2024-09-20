package br.com.sky.xyinc.core.exception

import br.com.sky.xyinc.core.domain.RegistryFieldError

class RegistryInvalidFieldsException(
    val errors: List<RegistryFieldError>
) : BusinessException(
    ErrorCode.REGISTRY_CREATE_INVALID,
    "Invalid registry with errors ${errors.map { "${it.field.name}|${it.errorType}" }}")