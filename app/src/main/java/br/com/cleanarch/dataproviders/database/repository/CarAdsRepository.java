package br.com.cleanarch.dataproviders.database.repository;

import br.com.cleanarch.dataproviders.database.entity.PersistentCarAds;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarAdsRepository extends JpaRepository<PersistentCarAds, Long> {
    Page<PersistentCarAds> findByCarAdsRequestId(Long searchRequestId, Pageable pageable);
}
