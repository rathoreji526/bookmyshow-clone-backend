package com.bookmyshow.bmscore.requestDTO;

import lombok.Getter;

import java.util.UUID;

@Getter
public class RegisterTheaterRequestDTO {
    private String theaterName;
    private String gstNumber;
    private String panNumber;
    private String businessLicenseNumber;
    private UUID ownerId;
    private LocationRequestDTO location;
}
