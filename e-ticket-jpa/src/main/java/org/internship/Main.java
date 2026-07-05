package org.internship;

import jakarta.persistence.*;
import org.internship.entity.*;
import org.internship.service.*;

import java.util.List;

public class Main {
    public static void main(String[] args){
        try(EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-e-ticket");
            EntityManager em = emf.createEntityManager()){
            FineService fineService = new FineService(em);
            CitizenService citizenService = new CitizenService(em);
            PaymentService paymentService = new PaymentService(em);
            PoliceService policeService = new PoliceService(em);
            VehicleService vehicleService = new VehicleService(em);

            try {
                // Create two officers
                System.out.println("--- 1. Creating officers ---");
                Police officer1 = policeService.createOfficer("Officer A", "48934", "officer_a", "123");
                Police officer2 = policeService.createOfficer("Officer B", "30480", "officer_b", "123");
                System.out.println(officer1);
                System.out.println(officer2);

                // Create three citizens
                System.out.println("\n--- 2. Creating citizens ---");
                Citizen citizen1 = citizenService.createCitizen("Citizen 1", "34344", "citizen_1", "123");
                Citizen citizen2 = citizenService.createCitizen("Citizen 2", "34800", "citizen_2", "123");
                Citizen citizen3 = citizenService.createCitizen("Citizen 3", "93839", "citizen_3", "123");
                System.out.println(citizen1);
                System.out.println(citizen2);
                System.out.println(citizen3);

                // Register vehicles for citizens
                System.out.println("\n--- 3. Registering vehicles ---");
                Vehicle vehicle1 = vehicleService.registerVehicle("AA111BB", "Suzuki Ignis", citizen1);
                Vehicle vehicle2 = vehicleService.registerVehicle("AA222BB", "Toyota Yaris", citizen2);
                System.out.println(vehicle1);
                System.out.println(vehicle2);

                // Create multiple fines
                System.out.println("\n--- 4. Creating fines ---");
                Fine fine1 = fineService.createFine("Speeding", 10000, officer1, vehicle1);
                Fine fine2 = fineService.createFine("Illegal parking", 20000, officer2, vehicle2);
                System.out.println(fine1);
                System.out.println(fine2);

                // Print all fines
                System.out.println("\n--- 5. All fines ---");
                printFines(fineService.findAllFines());

                // Search fines by citizen
                System.out.println("\n--- 6. Fines for citizen: " + citizen1.getName() + " ---");
                printFines(fineService.findFinesByCitizen(citizen1.getId()));

                // Search fines by plate number
                System.out.println("\n--- 7. Fines for plate: " + vehicle2.getPlateNumber() + " ---");
                printFines(fineService.findFinesByPlateNumber(vehicle2.getPlateNumber()));

                // Update one fine reason
                System.out.println("\n--- 8. Updating reason for fine #" + fine2.getId() + " ---");
                Fine updatedFine = fineService.updateFineReason(fine2.getId(), "Red Light Violation");
                System.out.println("Updated: " + updatedFine);

                // Pay one fine
                System.out.println("\n--- 9. Paying fine #" + fine1.getId() + " ---");
                Payment payment = paymentService.payFine(fine1.getId(), fine1.getAmount(), citizen1.getId());
                System.out.println(payment);

                // Try to pay the same fine again
                System.out.println("\n--- 10. Trying to pay fine #" + fine1.getId() + " again ---");
                try {
                    paymentService.payFine(fine1.getId(), fine1.getAmount(), citizen1.getId());
                } catch (IllegalStateException e) {
                    System.out.println("Error expected repaying the same fine: " + e.getMessage());
                }

                // Cancel one unpaid fine
                System.out.println("\n--- 11. Cancelling fine #" + fine2.getId() + " ---");
                Fine cancelledFine = fineService.cancelFine(fine2.getId());
                System.out.println("Cancelled: " + cancelledFine);

                // Try to pay the cancelled fine
                System.out.println("\n--- 12. Trying to pay cancelled fine #" + fine2.getId() + " ---");
                try {
                    paymentService.payFine(fine2.getId(), fine2.getAmount(), citizen2.getId());
                } catch (IllegalStateException e) {
                    System.out.println("Expected error: " + e.getMessage());
                }

                // Print final fine statuses
                System.out.println("\n--- 13. Final fine statuses ---");
                printFines(fineService.findAllFines());

            } catch (IllegalArgumentException | IllegalStateException | EntityExistsException |
                     EntityNotFoundException ex){
                System.out.println("\n--- ERROR ---");
                System.out.println(ex.getMessage());
            } finally {
                // Shutdown Hibernate
                System.out.println("\n--- 14. Shutdown Hibernate ---");
                em.close();
                emf.close();
                System.out.println("Goodbye :(");
            }
        }


    }

    private static void printFines(List<Fine> fines) {
        if (fines.isEmpty()) {
            System.out.println("(no fines found)");
            return;
        }
        for (Fine fine : fines) {
            System.out.println(fine);
        }
    }
}
