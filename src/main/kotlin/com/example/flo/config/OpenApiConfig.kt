package com.example.flo.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .info(Info()
                .title("Flo E-commerce API")
                .description("API for managing products, categories, and orders in the Flo e-commerce system")
                .version("1.0.0")
                .contact(Contact()
                    .name("Support Team")
                    .email("support@example.com")))
            .addServersItem(Server().url("/").description("Development server"))
    }
}
