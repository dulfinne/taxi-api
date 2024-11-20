package com.dulfinne.taxi.passengerservice.config;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

@Configuration
public class LocalizationConfig {

  @Bean
  public LocaleResolver localeResolver() {
    AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
    resolver.setDefaultLocale(Locale.ENGLISH);
    return resolver;
  }

  @Bean
  public MessageSource validationMessageSource() {
    ReloadableResourceBundleMessageSource messageSource =
        new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("classpath:localization/validation/validation");
    messageSource.setDefaultEncoding("ISO-8859-1");
    return messageSource;
  }

  @Bean
  public MessageSource exceptionMessageSource() {
    ReloadableResourceBundleMessageSource messageSource =
        new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("classpath:localization/exception/exception");
    messageSource.setDefaultEncoding("ISO-8859-1");
    return messageSource;
  }
}
