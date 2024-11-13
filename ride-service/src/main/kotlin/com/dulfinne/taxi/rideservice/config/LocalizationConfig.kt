package com.dulfinne.taxi.rideservice.config

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import java.util.Locale

@Configuration
class LocalizationConfig {
    @Bean
    fun localeResolver(): LocaleResolver {
        val resolver = AcceptHeaderLocaleResolver()
        resolver.setDefaultLocale(Locale.ENGLISH)
        return resolver
    }

    @Bean
    fun validationMessageSource(): MessageSource {
        val messageSource = ReloadableResourceBundleMessageSource()
        messageSource.setBasename("classpath:localization/validation/validation")
        messageSource.setDefaultEncoding("ISO-8859-1")
        return messageSource
    }

    @Bean
    fun exceptionMessageSource(): MessageSource {
        val messageSource = ReloadableResourceBundleMessageSource()
        messageSource.setBasename("classpath:localization/exception/exception")
        messageSource.setDefaultEncoding("ISO-8859-1")
        return messageSource
    }
}