package com.bookmyshow.bmscore.model;

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
@Table(name = "theatre_owner")
public class TheatreOwner extends User{
    @Column(nullable = false)
    private String panCard;
    @Column(nullable = false)
    private String businessRegistrationNumber;
    @Column(nullable = false)
    private String gstNumber;
}
