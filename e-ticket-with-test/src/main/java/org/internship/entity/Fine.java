package org.internship.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "fines")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private double amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FineStatus status = FineStatus.UNPAID;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "police_id", nullable = false)
    private Police police;

    @OneToOne(mappedBy = "fine")
    private Payment payment;

    public Fine(String reason, int amount, FineStatus status, Police officer, Vehicle vehicle) {
        this.reason = reason;
        this.amount = amount;
        this.status = status;
        this.police = officer;
        this.vehicle = vehicle;
    }

    @Override
    public String toString(){
        return "Fine: " + reason + " " + amount + " " + vehicle.getPlateNumber() + " " + status.name();
    }
}
