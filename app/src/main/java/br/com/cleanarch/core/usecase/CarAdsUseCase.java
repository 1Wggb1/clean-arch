package br.com.cleanarch.core.usecase;

import br.com.cleanarch.core.dataprovider.DatabaseProvider;
import br.com.cleanarch.core.entity.CarAdsBusiness;

import java.util.List;

public class CarAdsUseCase {
    private final DatabaseProvider databaseProvider;

    public CarAdsUseCase(final DatabaseProvider databaseProvider) {
        this.databaseProvider = databaseProvider;
    }

    public void createSearch() {
        databaseProvider.createSearch(null);
    }

    public List<CarAdsBusiness> findAdsById(final int id, final long page, final long size) {
        return databaseProvider.findSearch(id, page, size);
    }
}