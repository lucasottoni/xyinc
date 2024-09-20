package br.com.sky.xyinc.core.exception

abstract class NotFoundException(
    errorCode: ErrorCode, errorMessage: String,
) : BusinessException(errorCode, errorMessage)