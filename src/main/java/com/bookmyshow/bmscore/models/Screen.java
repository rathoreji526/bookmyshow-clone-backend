package com.bookmyshow.bmscore.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "screens")
public class Screen extends GlobalFields{
    private String name;
    private boolean isActive = true;

    @JsonBackReference
    @ManyToOne
    private Theater theater;

    @JsonManagedReference
    @OneToMany(mappedBy = "screen" , cascade = CascadeType.ALL ,orphanRemoval = true)
    private List<Row> rows = new ArrayList<>();

    public void addRow(Row row) {
        rows.add(row);
        row.setScreen(this);
    }
}
