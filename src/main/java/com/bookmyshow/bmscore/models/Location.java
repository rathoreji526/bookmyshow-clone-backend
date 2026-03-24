package com.bookmyshow.bmscore.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "locations")
public class Location extends GlobalFields{
    private String completeAddress;
    private String country;
    private String state;
    private String city;
    @Column(unique = true)
    private String zipcode;
}
