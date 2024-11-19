package br.com.cleanarch.configuration;

import br.com.cleanarch.core.dataprovider.CarAdsClientProvider;
import br.com.cleanarch.core.dataprovider.DatabaseProvider;
import br.com.cleanarch.dataproviders.client.CarAdsClientProviderImpl;
import br.com.cleanarch.dataproviders.database.DatabaseProviderConverter;
import br.com.cleanarch.dataproviders.database.DatabaseProviderImpl;
import br.com.cleanarch.dataproviders.database.repository.CarAdsRepository;
import br.com.cleanarch.dataproviders.database.repository.CarAdsRequestRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataProviderConfiguration {
    @Bean
    DatabaseProvider h2DataProvider(final CarAdsRequestRepository carAdsRequestRepository, final CarAdsRepository carAdsRepository) {
        return new DatabaseProviderImpl(carAdsRequestRepository, carAdsRepository, new DatabaseProviderConverter());
    }

    @Bean
    CarAdsClientProvider carAdsClientProvider() {
        return new CarAdsClientProviderImpl();
    }
}
