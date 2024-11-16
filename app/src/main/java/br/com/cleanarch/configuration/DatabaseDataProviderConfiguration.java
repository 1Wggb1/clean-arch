package br.com.cleanarch.configuration;

import br.com.cleanarch.core.dataprovider.DatabaseProvider;
import br.com.cleanarch.dataprovider.database.h2.H2ProviderImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseDataProviderConfiguration {
    @Bean
    public DatabaseProvider h2DataProvider() {
        return new H2ProviderImpl();
    }
}
