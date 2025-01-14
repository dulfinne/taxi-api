package com.dulfinne.taxi.authservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
    info =
        @Info(
            title = "Auth Api",
            description = "API for authentication",
            version = "1.0.0",
            contact = @Contact(name = "Zhukava Yana", email = "yanazhukava@gmail.com")))
public class SwaggerConfig {}
