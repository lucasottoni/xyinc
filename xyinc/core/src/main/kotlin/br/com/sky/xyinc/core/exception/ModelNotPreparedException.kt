package br.com.sky.xyinc.core.exception

class ModelNotPreparedException(modelName: String) :
    BusinessException(ErrorCode.MODEL_NOT_PREPARED, "Model $modelName not prepared") {
    init {
        addParameter("modelName", modelName)
    }
}