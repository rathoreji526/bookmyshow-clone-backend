package com.bookmyshow.bmscore.models;

import com.bookmyshow.bmscore.enums.RowType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rows")
public class Row extends GlobalFields{
    private String name;
    @Enumerated(EnumType.STRING)
    private RowType category;

    @ManyToOne
    private Screen screen;

    @OneToMany(mappedBy = "row", cascade = CascadeType.ALL)
    private List<Seat> seats;
}
