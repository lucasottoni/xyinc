package br.com.sky.xyinc.core.exception

class ModelAlreadyExistsException(modelName: String) :
    BusinessException(ErrorCode.MODEL_ALREADY_EXISTS, "Model $modelName already exists") {
    init {
        addParameter("modelName", modelName)
    }
}