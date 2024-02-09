package project.vilsoncake.Flowt.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import project.vilsoncake.Flowt.property.ApplicationProperties;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class CorsConfig {

    private final ApplicationProperties applicationProperties;

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin(applicationProperties.getProxyClientUrl());
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "access-control-allow-origin", "*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(source);
    }
}
