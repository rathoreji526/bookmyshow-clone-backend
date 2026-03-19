package com.bookmyshow.bmscore.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Theater extends GlobalFields{
    private String name;
    private boolean isActive;
    @ManyToOne
    private Location location;
    @ManyToOne
    private User owner;
}
