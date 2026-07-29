package org.internship.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private double amount;

    @OneToOne
    @JoinColumn(name = "fine_id", nullable = false)
    private Fine fine;

    @ManyToOne
    @JoinColumn(name = "payer_id", nullable = false)
    private Citizen payer;

    public Payment(double amount, Fine updatedFine, Citizen payer) {
        this.amount = amount;
        this.fine = updatedFine;
        this.payer = payer;
    }

    @Override
    public String toString(){
        return "Payment for fine: " + fine.getId() + " " + amount + " " + payer.getName();
    }
}
