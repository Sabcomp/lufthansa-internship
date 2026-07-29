package org.internship.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehicles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String plateNumber;

    @Column(nullable = false)
    private String model;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Citizen owner;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Fine> fines = new ArrayList<>();

    public Vehicle(String plateNumber, String model, Citizen owner) {
        this.plateNumber = plateNumber;
        this.model = model;
        this.owner = owner;
    }

    @Override
    public String toString(){
        return "Vehicle: " + plateNumber + " " + model + " " + owner.getName();
    }
}
