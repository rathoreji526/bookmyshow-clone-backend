package com.bookmyshow.bmscore.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Screen extends GlobalFields{
    private String name;
    private boolean isActive;

    @ManyToOne
    private Theater theater;

    @OneToMany(mappedBy = "screen")
    private List<Row> rows;
}
