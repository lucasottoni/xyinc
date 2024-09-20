package br.com.sky.xyinc.core.exception

class ModelNotFoundException(modelName: String) :
    NotFoundException(ErrorCode.MODEL_NOT_FOUND, "Model $modelName not found") {

    init {
        addParameter("modelName", modelName)
    }
}