package br.com.sky.xyinc.api.openapi

import br.com.sky.xyinc.core.domain.FieldType
import br.com.sky.xyinc.core.domain.ModelDefinition
import br.com.sky.xyinc.core.port.input.definition.ListModelDefinitionPort
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.PathItem
import io.swagger.v3.oas.models.media.ArraySchema
import io.swagger.v3.oas.models.media.Content
import io.swagger.v3.oas.models.media.IntegerSchema
import io.swagger.v3.oas.models.media.MediaType
import io.swagger.v3.oas.models.media.NumberSchema
import io.swagger.v3.oas.models.media.ObjectSchema
import io.swagger.v3.oas.models.media.Schema
import io.swagger.v3.oas.models.media.StringSchema
import io.swagger.v3.oas.models.parameters.Parameter
import io.swagger.v3.oas.models.parameters.RequestBody
import io.swagger.v3.oas.models.responses.ApiResponse
import io.swagger.v3.oas.models.responses.ApiResponses
import org.springdoc.core.customizers.OpenApiCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE


@Configuration
class OpenApiConfiguration(
    private val listModelDefinitionPort: ListModelDefinitionPort
) {

    @Bean
    fun customerGlobalHeaderOpenApiCustomizer(): OpenApiCustomizer {
        val models = listModelDefinitionPort.listAll()
        return OpenApiCustomizer { openApi ->
            models.forEach { addRegistryPath(openApi, it) }
        }
    }

    fun addRegistryPath(openApi: OpenAPI, model: ModelDefinition) {
        val (schemaWithoutId, schemaWithId) = createSchema(model)
        openApi.components.addSchemas(
            model.name,
            schemaWithId
        )
        openApi.components.addSchemas(
            schemaWithoutId.name,
            schemaWithoutId
        )
        val arrayWithId = ArraySchema().items(schemaWithId)

        openApi
            .path(
                "/v1/registry/${model.name}",
                PathItem()
                    .get(createOperation(model.name, "ListAll", null, arrayWithId))
                    .post(createOperation(model.name, "Create", schemaWithoutId, arrayWithId))
            )
            .path(
                "/v1/registry/${model.name}/{id}",
                PathItem()
                    .get(createOperation(model.name, "GetById", null, schemaWithId))
                    .put(createOperation(model.name, "PutById", schemaWithoutId, schemaWithId))
                    .delete(createOperation(model.name, "DeleteById", null, null))
            )
    }

    private fun createOperation(
        modelName: String,
        operationName: String,
        requestSchema: Schema<*>?,
        responseSchema: Schema<*>?
    ): Operation {
        val operation = Operation().operationId(modelName + operationName)

        operation.addTagsItem(modelName)

        if (operationName.endsWith("ById")) {
            operation.addParametersItem(Parameter().name("id").schema(IntegerSchema()).`in`("path"))
        }

        val createContent = { schema: Schema<*> ->
            Content().addMediaType(APPLICATION_JSON_VALUE, MediaType().schema(schema))
        }

        responseSchema?.also {
            operation.responses(
                ApiResponses().addApiResponse(
                    "200",
                    ApiResponse().description("OK").content(createContent(it))
                )
            )
        }

        requestSchema?.also {
            operation.requestBody(RequestBody().content(createContent(it)))
        }

        return operation
    }

    private fun createSchema(model: ModelDefinition): Pair<ObjectSchema, ObjectSchema> {
        val schemaWithoutId = ObjectSchema()
        schemaWithoutId.title("${model.name}Req").name("${model.name}Req")
        val schemaWithId = ObjectSchema()
        schemaWithId.title(model.name).name(model.name)

        model.fields.forEach { field ->
            val fieldSchema = toSchemaField(field.type)
            schemaWithoutId.addProperty(field.name, fieldSchema)
        }
        schemaWithoutId.required(model.fields.filter { it.required }.map { it.name })

        schemaWithId.addProperty("id", IntegerSchema())
        schemaWithoutId.properties.forEach { p ->
            schemaWithId.addProperty(p.key, p.value)
        }
        schemaWithId.required(schemaWithoutId.required + "id")

        return Pair(schemaWithoutId, schemaWithId)
    }

    private fun toSchemaField(type: FieldType): Schema<*> {
        return when (type) {
            FieldType.INTEGER -> IntegerSchema()
            FieldType.DECIMAL -> NumberSchema()
            FieldType.DOUBLE -> NumberSchema()
            FieldType.TEXT -> StringSchema()
        }
    }
}