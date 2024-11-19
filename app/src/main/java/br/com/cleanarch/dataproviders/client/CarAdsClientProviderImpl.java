package br.com.cleanarch.dataproviders.client;

import br.com.cleanarch.core.dataprovider.CarAdsClientProvider;
import br.com.cleanarch.core.entity.CarAdsBusiness;
import br.com.cleanarch.core.entity.CarAdsRequestBusiness;
import br.com.cleanarch.dataproviders.client.exception.CarAdsClientException;

import java.util.List;

public class CarAdsClientProviderImpl implements CarAdsClientProvider {
    @Override
    public List<CarAdsBusiness> findAds(final CarAdsRequestBusiness carAdsRequestBusiness) {
        //http call and return result
        try {
            return List.of();
        } catch (final Exception exception) {
            throw new CarAdsClientException(exception);
        }
    }
}
