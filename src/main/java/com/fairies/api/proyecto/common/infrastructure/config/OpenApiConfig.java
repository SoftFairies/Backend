package com.fairies.api.proyecto.common.infrastructure.config;

import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.lang.reflect.Method;

@Configuration
public class OpenApiConfig {

    @Bean
    public OperationCustomizer globalErrorResponsesCustomizer() {
        return (operation, handlerMethod) -> {
            ApiResponses responses = operation.getResponses();
            Method method = handlerMethod.getMethod();

            // 1. Errores universales para cualquier endpoint
            responses.addApiResponse("401", new ApiResponse().description("Unauthorized - No autenticado"));
            responses.addApiResponse("403", new ApiResponse().description("Forbidden - Sin permisos necesarios"));
            responses.addApiResponse("500", new ApiResponse().description("Internal Server Error - Error inesperado"));

            // 2. Detección de tipo de operación
            boolean isRead = method.isAnnotationPresent(GetMapping.class);
            boolean isWrite = method.isAnnotationPresent(PostMapping.class) ||
                    method.isAnnotationPresent(PutMapping.class) ||
                    method.isAnnotationPresent(DeleteMapping.class);

            // 3. Aplicación inteligente de errores
            if (isRead || isWrite) {
                responses.addApiResponse("404", new ApiResponse().description("Not Found - Recurso no encontrado"));
            }

            if (isWrite) {
                responses.addApiResponse("400", new ApiResponse().description("Bad Request - Errores de validación o formato"));
                responses.addApiResponse("409", new ApiResponse().description("Conflict - Violación de integridad de datos"));
            }

            return operation;
        };
    }
}