package br.com.cleanarch.dataproviders.database.entity;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "car_ads_request")
public class PersistentCarAdRequest {
    @Id
    @GeneratedValue(strategy = IDENTITY, generator = "car_ads_req_sequence_gen")
    @SequenceGenerator(name = "car_ads_req_sequence_gen", allocationSize = 1)
    private Long id;
    @Column(name = "brand")
    private String brand;
    @Column(name = "model")
    private String model;
    @Column(name = "city")
    private String city;
    @Column(name = "minKm")
    private Double minKm;
    @Column(name = "maxKm")
    private Double maxKm;
    @Column(name = "minYear")
    private Long minYear;
    @Column(name = "maxYear")
    private Long maxYear;

    public PersistentCarAdRequest() {
    }

    public PersistentCarAdRequest(final Long id, final String brand, final String model, final String city, final Double minKm, final Double maxKm, final Long minYear, final Long maxYear) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.city = city;
        this.minKm = minKm;
        this.maxKm = maxKm;
        this.minYear = minYear;
        this.maxYear = maxYear;
    }

    public PersistentCarAdRequest(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}