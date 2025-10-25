package br.ufpi.recicle_ai.config;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(info = @Info(title = "RecicleAI API", version = "v1", description = "Documentation of RecicleAI API"))
public class SpringDocConfig {
}
