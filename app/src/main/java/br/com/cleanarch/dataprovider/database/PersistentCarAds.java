package br.com.cleanarch.dataprovider.database;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "car_ads")
public class PersistentCarAds {
    @Id
    @GeneratedValue(strategy = IDENTITY, generator = "car_sequence_gen")
    @SequenceGenerator(name = "car_sequence_gen", allocationSize = 1)
    private Long id;
}
