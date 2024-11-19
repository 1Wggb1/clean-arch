package br.com.cleanarch.core.dataprovider;

import br.com.cleanarch.core.entity.CarAdsBusiness;
import br.com.cleanarch.core.entity.CarAdsRequestBusiness;
import br.com.cleanarch.core.entity.PageableCarAdsBusiness;

import java.util.List;

public interface DatabaseProvider {
    long createSearch(CarAdsRequestBusiness carAdsRequestBusiness);

    void saveSearchResults(long searchRequestId, List<CarAdsBusiness> carAdsBusiness);

    PageableCarAdsBusiness findSearch(long searchRequestId, long page, long size);
}
