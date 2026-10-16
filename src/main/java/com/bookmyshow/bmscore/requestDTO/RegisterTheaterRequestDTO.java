package com.bookmyshow.bmscore.requestDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class RegisterTheaterRequestDTO {
    private String theaterName;
    private String gstNumber;
    private String panNumber;
    private String businessLicenseNumber;
    private LocationRequestDTO location;
}
