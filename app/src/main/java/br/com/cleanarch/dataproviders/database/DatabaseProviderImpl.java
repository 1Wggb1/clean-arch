package br.com.cleanarch.dataproviders.database;

import br.com.cleanarch.core.dataprovider.DatabaseProvider;
import br.com.cleanarch.core.entity.CarAdsBusiness;
import br.com.cleanarch.core.entity.CarAdsRequestBusiness;
import br.com.cleanarch.core.entity.PageableCarAdsBusiness;
import br.com.cleanarch.dataproviders.database.entity.PersistentCarAdRequest;
import br.com.cleanarch.dataproviders.database.entity.PersistentCarAds;
import br.com.cleanarch.dataproviders.database.repository.CarAdsRepository;
import br.com.cleanarch.dataproviders.database.repository.CarAdsRequestRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class DatabaseProviderImpl implements DatabaseProvider {
    private final CarAdsRequestRepository carAdsRequestRepository;
    private final CarAdsRepository carAdsRepository;
    private final DatabaseProviderConverter databaseProviderConverter;

    public DatabaseProviderImpl(final CarAdsRequestRepository carAdsRequestRepository, final CarAdsRepository carAdsRepository, final DatabaseProviderConverter databaseProviderConverter) {
        this.carAdsRequestRepository = carAdsRequestRepository;
        this.carAdsRepository = carAdsRepository;
        this.databaseProviderConverter = databaseProviderConverter;
    }

    @Override
    public long createSearch(final CarAdsRequestBusiness carAdsRequestBusiness) {
        final PersistentCarAdRequest saved = carAdsRequestRepository.save(databaseProviderConverter.toPersistent(carAdsRequestBusiness));
        return saved.getId();
    }

    @Override
    public void saveSearchResults(final long searchRequestId, final List<CarAdsBusiness> carAdsBusiness) {
        carAdsRepository.saveAll(databaseProviderConverter.toPersistent(searchRequestId, carAdsBusiness));
    }

    @Transactional(readOnly = true)
    @Override
    public PageableCarAdsBusiness findSearch(final long searchRequestId, final long page, final long size) {
        final Page<PersistentCarAds> persistentCarAdsPage = carAdsRepository.findByCarAdsRequestId(searchRequestId, PageRequest.of((int) page, (int) size));
        return databaseProviderConverter.toBusiness(persistentCarAdsPage);
    }
}