package br.com.sky.xyinc.repository.entity

import br.com.sky.xyinc.core.domain.FieldDefinition
import br.com.sky.xyinc.core.domain.ModelDefinition
import br.com.sky.xyinc.repository.converter.jsonNodeToObject
import br.com.sky.xyinc.repository.converter.objectToJsonNode
import com.fasterxml.jackson.databind.JsonNode
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.envers.Audited
import org.hibernate.type.SqlTypes

@Audited
@Entity
@Table(name = "model_definition")
data class ModelDefinitionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val name: String,
    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    val fields: JsonNode
) {

    fun toDomain(): ModelDefinition {
        val fields: List<FieldDefinitionEntity> = this.fields.jsonNodeToObject()
        return ModelDefinition(
            id = this.id,
            name = this.name,
            fields = fields.map { it.toDomain() }
        )
    }

    companion object {
        fun toEntity(fields: List<FieldDefinition>): JsonNode {
            return fields.map {
                FieldDefinitionEntity(name = it.name, type = it.type, required = it.required)
            }.objectToJsonNode()
        }
    }
}
