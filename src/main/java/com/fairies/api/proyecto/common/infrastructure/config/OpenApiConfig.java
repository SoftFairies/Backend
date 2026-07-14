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

            // 1. Errores universales (presentes en todos los endpoints)
            responses.addApiResponse("401", new ApiResponse().description("No autenticado"));
            responses.addApiResponse("403", new ApiResponse().description("Sin permisos necesarios"));
            responses.addApiResponse("500", new ApiResponse().description("Error interno del servidor"));

            // 2. Detección de tipo de operación
            boolean isRead = method.isAnnotationPresent(GetMapping.class);
            boolean isWrite = method.isAnnotationPresent(PostMapping.class) ||
                    method.isAnnotationPresent(PutMapping.class) ||
                    method.isAnnotationPresent(DeleteMapping.class);

            if (isRead || isWrite) {
                responses.addApiResponse("404", new ApiResponse().description("Recurso no encontrado"));
            }

            if (isWrite) {
                responses.addApiResponse("400", new ApiResponse().description("Solicitud inválida o errores de validación"));
                responses.addApiResponse("409", new ApiResponse().description("Conflicto de estado o integridad"));
                responses.addApiResponse("422", new ApiResponse().description("Violación de regla de negocio"));
            }

            return operation;
        };
    }
}