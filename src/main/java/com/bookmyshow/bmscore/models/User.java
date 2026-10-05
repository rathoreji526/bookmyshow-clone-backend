package com.bookmyshow.bmscore.models;

import com.bookmyshow.bmscore.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends GlobalFields{
    @NotNull
    private String name;

    @NotNull
    @Column(unique = true , nullable = false)
    private String username;

    @NotNull
    @Column( nullable = false)
    private String email;

    @NotNull
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean isActive = true;

    @OneToMany(mappedBy = "user" , cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Booking> bookings;
}
