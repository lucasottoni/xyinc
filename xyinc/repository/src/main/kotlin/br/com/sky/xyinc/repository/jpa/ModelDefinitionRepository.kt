package br.com.sky.xyinc.repository.jpa

import br.com.sky.xyinc.repository.entity.ModelDefinitionEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ModelDefinitionRepository : JpaRepository<ModelDefinitionEntity, Long> {
    fun findByName(name: String): ModelDefinitionEntity?
}