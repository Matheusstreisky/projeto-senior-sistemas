package com.seniorsistemas.project.config.docs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfigurations {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Projeto Senior Sistemas")
                        .version("v1.0.0")
                        .description("Projeto realizado como parte do processo de seleção da Senior Sistemas"));
    }
}
