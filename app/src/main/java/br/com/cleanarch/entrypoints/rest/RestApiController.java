package br.com.cleanarch.entrypoints.rest;

import br.com.cleanarch.api.CarsAdsApi;
import br.com.cleanarch.core.entity.CarAdsRequestBusiness;
import br.com.cleanarch.core.entity.PageableCarAdsBusiness;
import br.com.cleanarch.core.usecase.CarAdsUseCase;
import br.com.cleanarch.dto.AbstractCarAds;
import br.com.cleanarch.dto.CarAdsResponseList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class RestApiController implements CarsAdsApi {
    private final CarAdsUseCase carAdsUseCase;
    private final RestControllerConverter controllerConverter;

    public RestApiController(final CarAdsUseCase carAdsUseCase, final RestControllerConverter controllerConverter) {
        this.carAdsUseCase = carAdsUseCase;
        this.controllerConverter = controllerConverter;
    }

    @Override
    public ResponseEntity<AbstractCarAds> createCarAdsSearch(final AbstractCarAds abstractCarAds) {
        final CarAdsRequestBusiness carAdsRequestBusiness = controllerConverter.toBusiness(abstractCarAds);
        final long search = carAdsUseCase.createSearch(carAdsRequestBusiness);
        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .queryParam("page", 0)
                .queryParam("size", 10)
                .buildAndExpand(search)
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @Override
    public ResponseEntity<CarAdsResponseList> findCarAdsById(final Long carAdsSearchId, final Long page, final Long size) {
        final PageableCarAdsBusiness pageableCarAdsBusiness = carAdsUseCase.findAdsById(carAdsSearchId, page, size);
        return ResponseEntity.ok(controllerConverter.toDto(pageableCarAdsBusiness));
    }
}
