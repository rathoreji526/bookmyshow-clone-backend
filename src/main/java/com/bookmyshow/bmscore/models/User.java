package com.bookmyshow.bmscore.models;

import com.bookmyshow.bmscore.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends GlobalFields{
    @NotNull
    private String name;
    @NotNull
    @Column(unique = true ,  nullable = false)
    private String email;
    @NotNull
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean isActive;
}
