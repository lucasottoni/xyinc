package br.com.sky.xyinc.core.exception

class ModelNotEmptyException(modelName: String, qtdRegistry: Long) :
    BusinessException(ErrorCode.MODEL_NOT_EMPTY, "Model $modelName has $qtdRegistry values") {

    init {
        addParameter("modelName", modelName)
        addParameter("qtdRegistry", qtdRegistry.toString())
    }
}