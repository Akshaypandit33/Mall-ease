package com.akshay.backend.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID paymentId;

    @ManyToOne   // Many Payments can belong to one User
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @OneToOne
    private Cart cart;
    private BigDecimal totalAmount;
    private LocalDateTime paymentDate;

    private String transactionId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}
