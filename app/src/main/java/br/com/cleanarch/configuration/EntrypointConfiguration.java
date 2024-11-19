package br.com.cleanarch.configuration;

import br.com.cleanarch.core.usecase.CarAdsUseCase;
import br.com.cleanarch.entrypoints.rest.RestApiController;
import br.com.cleanarch.entrypoints.rest.RestControllerConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EntrypointConfiguration {
    @Bean
    RestApiController restApiController(final CarAdsUseCase carAdsUseCase) {
        return new RestApiController(carAdsUseCase, new RestControllerConverter());
    }
}
