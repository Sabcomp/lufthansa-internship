package org.internship.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "citizens")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Citizen extends User{
    @Column(nullable = false, unique = true)
    private String driverId;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Vehicle> vehicles = new ArrayList<>();

    @OneToMany(mappedBy = "payer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();

    public Citizen(String name, String username, String password, String driverId){
        super(name, username, password);
        this.driverId = driverId;
    }
    @Override
    public String toString(){
        return "Citizen: " + super.getName() + " " + driverId + " " + super.getUsername();
    }
}
