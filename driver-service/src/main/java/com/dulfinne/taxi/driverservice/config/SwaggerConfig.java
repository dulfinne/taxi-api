package com.dulfinne.taxi.driverservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
    info =
        @Info(
            title = "Driver Api",
            description = "API for driver",
            version = "1.0.0",
            contact =
                @Contact(
                    name = "Zhukava Yana",
                    email = "yanazhukava@gmail.com")))
public class SwaggerConfig {}
