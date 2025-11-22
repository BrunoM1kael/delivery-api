package com.delivery_api.Projeto.Delivery.API.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Delivery Tech API")
                        .version("1.0.0")
                        .description("API para gestão de delivery de restaurantes")
                        .contact(new Contact()
                                .name("Seu Nome")
                                .email("seu.email@exemplo.com")));
    }
}