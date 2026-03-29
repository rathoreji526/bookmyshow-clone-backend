package com.bookmyshow.bmscore.requestDTO;

import lombok.Getter;

@Getter
public class LocationRequestDTO {
    private String country;
    private String state;
    private String city;
    private String area;
    private String street;
    private String landmark;
    private String zipcode;
}
