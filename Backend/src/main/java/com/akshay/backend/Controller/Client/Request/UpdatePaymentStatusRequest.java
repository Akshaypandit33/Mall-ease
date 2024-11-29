package com.akshay.backend.Controller.Client.Request;

import com.akshay.backend.Model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePaymentStatusRequest {

    private UUID paymentId;
    private String transactionId;
    private PaymentStatus paymentStatus;

}
