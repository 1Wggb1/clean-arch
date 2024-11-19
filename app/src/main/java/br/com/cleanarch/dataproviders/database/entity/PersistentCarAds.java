package br.com.cleanarch.dataproviders.database.entity;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "car_ads")
public class PersistentCarAds {
    @Id
    @GeneratedValue(strategy = IDENTITY, generator = "car_sequence_gen")
    @SequenceGenerator(name = "car_sequence_gen", allocationSize = 1)
    private Long id;
    private Double km;
    private String city;
    private Double price;
    private String seller;
    @Column(name = "modelYear")
    private Long modelYear;
    @Column(name = "modelFabricationYear")
    private Long modelFabricationYear;
    @ManyToOne
    @JoinColumn(name = "card_ads_req_id")
    private PersistentCarAdRequest carAdsRequest;

    public PersistentCarAds() {
    }

    public PersistentCarAds(final Long id, final Double km, final String city, final Double price, final String seller, final Long modelYear, final Long modelFabricationYear, final PersistentCarAdRequest carAdsRequest) {
        this.id = id;
        this.km = km;
        this.city = city;
        this.price = price;
        this.seller = seller;
        this.modelYear = modelYear;
        this.modelFabricationYear = modelFabricationYear;
        this.carAdsRequest = carAdsRequest;
    }

    public Long getId() {
        return id;
    }

    public Double getKm() {
        return km;
    }

    public String getCity() {
        return city;
    }

    public Double getPrice() {
        return price;
    }

    public String getSeller() {
        return seller;
    }

    public Long getModelYear() {
        return modelYear;
    }

    public Long getModelFabricationYear() {
        return modelFabricationYear;
    }

    public PersistentCarAdRequest getCarAdsRequest() {
        return carAdsRequest;
    }
}
