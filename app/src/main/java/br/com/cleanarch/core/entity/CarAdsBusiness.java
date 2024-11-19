package br.com.cleanarch.core.entity;

public record CarAdsBusiness(Long id,
                             String brand,
                             String model,
                             String city,
                             Double km,
                             Long modelFabricationYear,
                             Long modelYear,
                             Double price,
                             String seller) {
}
