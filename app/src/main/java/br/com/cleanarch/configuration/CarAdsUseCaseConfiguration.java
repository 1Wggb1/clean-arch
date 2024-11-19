package br.com.cleanarch.configuration;

import br.com.cleanarch.core.dataprovider.CarAdsClientProvider;
import br.com.cleanarch.core.dataprovider.DatabaseProvider;
import br.com.cleanarch.core.usecase.CarAdsUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarAdsUseCaseConfiguration {
    @Bean
    CarAdsUseCase carAdsUseCase(final DatabaseProvider databaseProvider, final CarAdsClientProvider carAdsClientProvider) {
        return new CarAdsUseCase(databaseProvider, carAdsClientProvider);
    }
}