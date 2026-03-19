package com.bookmyshow.bmscore.models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Location extends GlobalFields{
    private String country;
    private String state;
    private String city;
    private String zipcode;
}
