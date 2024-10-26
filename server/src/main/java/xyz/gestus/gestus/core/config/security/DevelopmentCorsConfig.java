package xyz.gestus.gestus.core.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Profile("development")
public class DevelopmentCorsConfig implements WebMvcConfigurer {

    @Value("${CORS_ALLOWED_ORIGIN}")
    private String corsAllowedOrigin;

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins(corsAllowedOrigin) // Adjust to your frontend's URL
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}
