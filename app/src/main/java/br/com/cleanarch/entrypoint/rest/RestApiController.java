package br.com.cleanarch.entrypoint.rest;

import br.com.cleanarch.api.CarsAdsApi;
import br.com.cleanarch.dto.AbstractCarAds;
import br.com.cleanarch.dto.CarAdsResponseList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiController implements CarsAdsApi {
    @Override
    public ResponseEntity<AbstractCarAds> createCarAdsSearch(final AbstractCarAds abstractCarAds) {
        return null;
    }

    @Override
    public ResponseEntity<CarAdsResponseList> findCarAdsBySearchId(final Long carAdsSearchId, final Long page, final Long size) {
        return null;
    }
}
