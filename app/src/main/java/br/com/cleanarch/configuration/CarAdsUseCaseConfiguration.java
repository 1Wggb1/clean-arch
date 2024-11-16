package br.com.cleanarch.configuration;

import br.com.cleanarch.core.dataprovider.DatabaseProvider;
import br.com.cleanarch.core.usecase.CarAdsUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarAdsUseCaseConfiguration {
    @Bean
    public CarAdsUseCase carAdsUseCase(DatabaseProvider databaseProvider){
        return new CarAdsUseCase(databaseProvider);
    }
}