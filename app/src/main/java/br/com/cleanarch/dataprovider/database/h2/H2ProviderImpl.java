package br.com.cleanarch.dataprovider.database.h2;

import br.com.cleanarch.core.dataprovider.DatabaseProvider;
import br.com.cleanarch.core.entity.CarAdsBusiness;

import java.util.List;

public class H2ProviderImpl implements DatabaseProvider {
    @Override
    public long createSearch(final CarAdsBusiness carAdsBusiness) {

        return 0L;
    }

    @Override
    public List<CarAdsBusiness> findSearch(final long id, final long page, final long size) {
        return List.of();
    }
}