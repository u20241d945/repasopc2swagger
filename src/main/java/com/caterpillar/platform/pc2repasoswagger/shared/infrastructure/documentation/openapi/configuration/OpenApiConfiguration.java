package com.caterpillar.platform.pc2repasoswagger.shared.infrastructure.documentation.openapi.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Provides the public OpenAPI metadata displayed by Swagger UI.
 *
 * @author Gabriel Yanac Flores
 */
@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI caterpillarMiningOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Caterpillar Mining Equipment API")
                .description("Registers Caterpillar mining equipment units")
                .version("v1"));
    }
}
