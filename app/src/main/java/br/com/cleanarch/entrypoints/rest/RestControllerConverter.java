package br.com.cleanarch.entrypoints.rest;

import br.com.cleanarch.core.entity.CarAdsBusiness;
import br.com.cleanarch.core.entity.CarAdsRequestBusiness;
import br.com.cleanarch.core.entity.PageableCarAdsBusiness;
import br.com.cleanarch.dto.AbstractCarAds;
import br.com.cleanarch.dto.CarAdsPageable;
import br.com.cleanarch.dto.CarAdsResponseItem;
import br.com.cleanarch.dto.CarAdsResponseList;

import java.util.List;

public class RestControllerConverter {
    public CarAdsRequestBusiness toBusiness(final AbstractCarAds carAdsRequest) {
        final String brand = carAdsRequest.getBrand();
        final String model = carAdsRequest.getModel();
        final String city = carAdsRequest.getCity();
        final Double minKm = carAdsRequest.getMinKm();
        final Double maxKm = carAdsRequest.getMaxKm();
        final Long minYear = carAdsRequest.getMinYear();
        final Long maxYear = carAdsRequest.getMaxYear();
        final Double price = carAdsRequest.getPrice();
        return new CarAdsRequestBusiness(null, brand, model, city, minKm, maxKm, minYear, maxYear, price);
    }

    public CarAdsResponseList toDto(final PageableCarAdsBusiness pageableCarAdsBusiness) {
        final CarAdsPageable carAdsPageable = CarAdsPageable.builder().page(pageableCarAdsBusiness.page())
                .size(pageableCarAdsBusiness.size())
                .build();
        return CarAdsResponseList.builder()
                .pageable(carAdsPageable)
                .results(toDto(pageableCarAdsBusiness.results()))
                .build();
    }

    private List<CarAdsResponseItem> toDto(final List<CarAdsBusiness> carAdsBusiness) {
        return carAdsBusiness.stream()
                .map(this::toDto)
                .toList();
    }

    private CarAdsResponseItem toDto(final CarAdsBusiness carAdsBusiness) {
        final Long id = carAdsBusiness.id();
        final String brand = carAdsBusiness.brand();
        final String model = carAdsBusiness.model();
        final Double km = carAdsBusiness.km();
        final String city = carAdsBusiness.city();
        final Long modelYear = carAdsBusiness.modelYear();
        final Long modelFabricationYear = carAdsBusiness.modelFabricationYear();
        final Double price = carAdsBusiness.price();
        return CarAdsResponseItem.builder()
                .id(id)
                .brand(brand)
                .model(model)
                .km(km)
                .city(city)
                .modelYear(modelYear)
                .modelFabricationYear(modelFabricationYear)
                .price(price)
                .build();
    }
}
