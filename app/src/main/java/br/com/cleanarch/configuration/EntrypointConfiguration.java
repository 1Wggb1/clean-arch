package br.com.cleanarch.configuration;

import br.com.cleanarch.entrypoint.rest.RestApiController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EntrypointConfiguration {
    @Bean
    public RestApiController restApiController() {
        return new RestApiController();
    }
}
