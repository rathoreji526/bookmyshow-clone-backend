package com.bookmyshow.bmscore.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "theaters")
public class Theater extends GlobalFields{
    private String name;
    private boolean isActive = true;
    private String gstNumber;
    private String panNumber;
    private String businessLicenseNumber;
    @JsonBackReference
    @ManyToOne
    private Location location;
    @JsonBackReference
    @ManyToOne
    private User owner;
}
