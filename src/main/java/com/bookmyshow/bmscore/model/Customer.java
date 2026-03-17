package com.bookmyshow.bmscore.model;

import com.bookmyshow.bmscore.enums.MembershipType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")
public class Customer extends User{
    private int loyaltyPoints = 0;
    @Enumerated(EnumType.STRING)
    private MembershipType membershipType;
}
