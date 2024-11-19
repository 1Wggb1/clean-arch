package br.com.cleanarch.dataproviders.database.repository;

import br.com.cleanarch.dataproviders.database.entity.PersistentCarAdRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarAdsRequestRepository extends JpaRepository<PersistentCarAdRequest, Long> {
}
