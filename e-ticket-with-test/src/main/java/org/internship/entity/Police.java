package org.internship.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "police")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Police extends User{
    @Column(nullable = false, unique = true)
    private String badgeId;

    public Police(String name, String username, String password, String badgeId){
        super(name, username, password);
        this.badgeId = badgeId;
    }

    @OneToMany(mappedBy = "police", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Fine> fines = new ArrayList<>();

    @Override
    public String toString(){
        return "Officer: " + super.getName() + " " + badgeId + " " + super.getUsername();
    }
}
