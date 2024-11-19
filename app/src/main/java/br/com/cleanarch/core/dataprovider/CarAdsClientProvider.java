package br.com.cleanarch.core.dataprovider;

import br.com.cleanarch.core.entity.CarAdsBusiness;
import br.com.cleanarch.core.entity.CarAdsRequestBusiness;

import java.util.List;

public interface CarAdsClientProvider {
    List<CarAdsBusiness> findAds(CarAdsRequestBusiness carAdsRequestBusiness);
}
