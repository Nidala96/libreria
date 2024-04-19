package it.fabio.libreria.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    private final String frontEndUrl = System.getenv("FRONT_END_URL");

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/libro/**")
                .allowedOrigins(frontEndUrl)
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true);
    }
}

