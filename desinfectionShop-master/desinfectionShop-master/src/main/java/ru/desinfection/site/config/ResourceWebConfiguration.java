package ru.desinfection.site.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.desinfection.site.DTO.SessionOrderedItem;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

@Configuration
public class ResourceWebConfiguration implements WebMvcConfigurer {

    @Bean
    @SessionScope
    public LinkedHashMap<Long, SessionOrderedItem> sessionCart() {
        return new LinkedHashMap<>();
    }

    @Bean
    @SessionScope
    public BigDecimal sessionTotalCost() {
        return BigDecimal.ZERO;
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("file:images/");
    }
}