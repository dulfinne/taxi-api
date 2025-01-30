package com.dulfinne.taxi.rideservice.config

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import java.nio.charset.StandardCharsets
import java.util.Locale

@Configuration
class LocalizationConfig {
    @Bean
    fun localeResolver(): LocaleResolver = AcceptHeaderLocaleResolver().apply {
        setDefaultLocale(Locale.ENGLISH)
    }

    @Bean
    fun validationMessageSource(): MessageSource = ReloadableResourceBundleMessageSource().apply {
        setBasename("classpath:localization/validation/validation")
        setDefaultEncoding(StandardCharsets.UTF_8.name())
    }

    @Bean
    fun exceptionMessageSource(): MessageSource = ReloadableResourceBundleMessageSource().apply {
        setBasename("classpath:localization/exception/exception")
        setDefaultEncoding(StandardCharsets.UTF_8.name())
    }
}