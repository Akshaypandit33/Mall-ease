package com.akshay.backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cartId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Reference to User entity

    @Column(nullable = false)
    private LocalDateTime createdOn;

    @Column(nullable = false)
    private LocalDateTime updatedOn;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private CartStatus status; // ACTIVE, ORDERED, ABANDONED

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItem> items= new ArrayList<>(); // List of Cart Items

    private BigDecimal totalPrice;

    // Method to calculate total price
    public void calculateTotalPrice() {
        this.totalPrice = items.stream()
                .map(CartItem::getTotalPrice)  // Assuming CartItem has a getTotalPrice method
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Summing all item prices
    }
}

