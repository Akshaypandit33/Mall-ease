package com.akshay.backend.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Receipt {
    @Id
    private UUID receipt_id;

    @OneToOne
    private Payment payment;

//    @OneToOne
//    private  Cart cart;

    @Lob  // This indicates a large object (BLOB)
    @Column(name = "qr_code", columnDefinition = "BLOB")
    private byte[] qr_code;

    private LocalDateTime generationDate;
    private LocalDateTime expirationTime;
    private LocalDateTime checkedOn;

    private ReceiptStatus receiptStatus;

}
