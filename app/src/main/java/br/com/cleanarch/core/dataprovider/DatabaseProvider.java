package br.com.cleanarch.core.dataprovider;

import br.com.cleanarch.core.entity.CarAdsBusiness;

import java.util.List;

public interface DatabaseProvider {
    long createSearch(CarAdsBusiness carAdsBusiness);

    List<CarAdsBusiness> findSearch(long id, long page, long size);
}
