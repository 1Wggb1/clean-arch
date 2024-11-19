package br.com.cleanarch.dataproviders.database;

import br.com.cleanarch.core.entity.CarAdsBusiness;
import br.com.cleanarch.core.entity.CarAdsRequestBusiness;
import br.com.cleanarch.core.entity.PageableCarAdsBusiness;
import br.com.cleanarch.dataproviders.database.entity.PersistentCarAdRequest;
import br.com.cleanarch.dataproviders.database.entity.PersistentCarAds;
import org.springframework.data.domain.Page;

import java.util.List;

public class DatabaseProviderConverter {
    public PersistentCarAdRequest toPersistent(final CarAdsRequestBusiness carAdsRequestBusiness) {
        return new PersistentCarAdRequest(null,
                carAdsRequestBusiness.brand(),
                carAdsRequestBusiness.model(),
                carAdsRequestBusiness.city(),
                carAdsRequestBusiness.minKm(),
                carAdsRequestBusiness.maxKm(),
                carAdsRequestBusiness.minYear(),
                carAdsRequestBusiness.maxYear());
    }

    public List<PersistentCarAds> toPersistent(final long searchRequestId, final List<CarAdsBusiness> carAdsBusiness) {
        return carAdsBusiness.stream()
                .map(carAds -> toPersistent(searchRequestId, carAds))
                .toList();
    }

    private PersistentCarAds toPersistent(final long searchRequestId, final CarAdsBusiness carAdsBusiness) {
        return new PersistentCarAds(null, carAdsBusiness.km(), carAdsBusiness.city(), carAdsBusiness.price(), carAdsBusiness.seller(), carAdsBusiness.modelYear(), carAdsBusiness.modelFabricationYear(), new PersistentCarAdRequest(searchRequestId));
    }

    public PageableCarAdsBusiness toBusiness(final Page<PersistentCarAds> persistentCarAdsPage) {
        return new PageableCarAdsBusiness(persistentCarAdsPage.getNumber(),
                persistentCarAdsPage.getSize(),
                persistentCarAdsPage.getTotalElements(),
                toBusiness(persistentCarAdsPage.getContent()));
    }

    private List<CarAdsBusiness> toBusiness(final List<PersistentCarAds> persistentCarAds) {
        return persistentCarAds.stream()
                .map(this::toBusiness)
                .toList();
    }

    private CarAdsBusiness toBusiness(final PersistentCarAds persistentCarAds) {
        return new CarAdsBusiness(persistentCarAds.getId(), null, null,
                persistentCarAds.getCity(),
                persistentCarAds.getKm(),
                persistentCarAds.getModelFabricationYear(),
                persistentCarAds.getModelYear(),
                persistentCarAds.getPrice(),
                persistentCarAds.getSeller());
    }
}