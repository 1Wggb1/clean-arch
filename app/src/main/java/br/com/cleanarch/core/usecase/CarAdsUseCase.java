package br.com.cleanarch.core.usecase;

import br.com.cleanarch.core.dataprovider.CarAdsClientProvider;
import br.com.cleanarch.core.dataprovider.DatabaseProvider;
import br.com.cleanarch.core.entity.CarAdsRequestBusiness;
import br.com.cleanarch.core.entity.PageableCarAdsBusiness;
import br.com.cleanarch.core.entity.exception.CarAdsBusinessException;

public class CarAdsUseCase {
    private final DatabaseProvider databaseProvider;
    private final CarAdsClientProvider carAdsClientProvider;

    public CarAdsUseCase(final DatabaseProvider databaseProvider, final CarAdsClientProvider carAdsClientProvider) {
        this.databaseProvider = databaseProvider;
        this.carAdsClientProvider = carAdsClientProvider;
    }

    public long createSearch(final CarAdsRequestBusiness carAdsRequestBusiness) {
        try {
            final long searchRequestId = databaseProvider.createSearch(carAdsRequestBusiness);
            doAdsResultSearch(searchRequestId, carAdsRequestBusiness);
            return searchRequestId;
        } catch (final Exception e) {
            throw new CarAdsBusinessException(e);
        }
    }

    private void doAdsResultSearch(final long searchRequestId, final CarAdsRequestBusiness carAdsRequestBusiness) {
        //assync search
        new Thread(() -> {
            databaseProvider.saveSearchResults(searchRequestId, carAdsClientProvider.findAds(carAdsRequestBusiness));
        }).start();
    }

    public PageableCarAdsBusiness findAdsById(final long searchRequestId, final long page, final long size) {
        try {
            return databaseProvider.findSearch(searchRequestId, page, size);
        } catch (final Exception e) {
            throw new CarAdsBusinessException(e);
        }
    }
}