package org.internship.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
import org.internship.entity.Citizen;
import org.internship.entity.Fine;
import org.internship.entity.FineStatus;
import org.internship.entity.Payment;
import org.internship.repository.CitizenRepository;
import org.internship.repository.FineRepository;
import org.internship.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaymentServiceTest {

    @Mock
    private EntityManager em;
    @Mock
    private EntityTransaction transaction;
    @Mock
    private FineRepository fineRepository;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private CitizenRepository citizenRepository;

    private PaymentService paymentService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        // use mock transaction
        when(em.getTransaction()).thenReturn(transaction);
        when(transaction.isActive()).thenReturn(false);

        // create paymentservice and inject dependencies
        paymentService = new PaymentService(em, fineRepository, paymentRepository, citizenRepository);
    }

    @Test
    public void testFineNotFound(){
        // when trying to find fine in repository, no fine found
        when(fineRepository.findById(1L)).thenReturn(null);

        // should throw EntityNotFoundException
        assertThrows(EntityNotFoundException.class, () -> paymentService.payFine(1L, 100, 2L));
    }

    @Test
    public void testIncorrectAmount(){
        Fine fine = mock(Fine.class);
        when(fine.getAmount()).thenReturn(100.0);

        when(fineRepository.findById(1L)).thenReturn(fine);

        // fine has amount 100, but payment is of value 80
        assertThrows(IllegalArgumentException.class, () -> paymentService.payFine(1L, 80, 2L));
    }

    @Test
    public void testPaidStatus(){
        Fine fine = mock(Fine.class);
        when(fine.getAmount()).thenReturn(100.0);
        when(fine.getStatus()).thenReturn(FineStatus.PAID);

        when(fineRepository.findById(1L)).thenReturn(fine);

        assertThrows(IllegalStateException.class, () -> paymentService.payFine(1L, 100, 2L));
    }

    @Test
    public void testCancelledStatus(){
        Fine fine = mock(Fine.class);
        when(fine.getAmount()).thenReturn(100.0);
        when(fine.getStatus()).thenReturn(FineStatus.CANCELLED);

        when(fineRepository.findById(1L)).thenReturn(fine);

        assertThrows(IllegalStateException.class, () -> paymentService.payFine(1L, 100, 2L));
    }

    @Test
    public void testCitizenNotFound(){
        Fine fine = mock(Fine.class);
        when(fine.getAmount()).thenReturn(100.0);
        when(fine.getStatus()).thenReturn(FineStatus.UNPAID);

        when(fineRepository.findById(1L)).thenReturn(fine);
        // citizen not found
        when(citizenRepository.findById(2L)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> paymentService.payFine(1L, 100, 2L));
    }

    @Test
    public void testPayFineSuccessfully(){
        long fineId = 1L, citizenId = 2L;
        double amount = 100.0;

        Fine fine = mock(Fine.class);
        when(fine.getAmount()).thenReturn(amount);
        when(fine.getStatus()).thenReturn(FineStatus.UNPAID);

        Citizen citizen = mock(Citizen.class);

        when(fineRepository.findById(fineId)).thenReturn(fine);
        when(citizenRepository.findById(citizenId)).thenReturn(citizen);
        when(fineRepository.update(fine)).thenReturn(fine);

        Payment result = paymentService.payFine(fineId, amount, citizenId);

        assertNotNull(result);
        assertEquals(amount, result.getAmount());
        assertEquals(fine, result.getFine());
        assertEquals(citizen, result.getPayer());

        // verify that setStatus to PAID was called
        verify(fine).setStatus(FineStatus.PAID);
        // verify that necessary fine and payment repository methods were called
        verify(fineRepository).update(fine);
        verify(paymentRepository).save(any(Payment.class));
    }

}
