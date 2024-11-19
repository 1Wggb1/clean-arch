package br.com.cleanarch.core.entity;

public record CarAdsRequestBusiness(Long id,
                                    String brand,
                                    String model,
                                    String city,
                                    Double minKm,
                                    Double maxKm,
                                    Long minYear,
                                    Long maxYear,
                                    Double price) {
}