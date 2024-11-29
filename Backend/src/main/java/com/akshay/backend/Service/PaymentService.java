package com.akshay.backend.Service;

import com.akshay.backend.Model.Payment;
import com.akshay.backend.Model.PaymentStatus;
import com.akshay.backend.Model.User;

import java.util.List;
import java.util.UUID;

public interface PaymentService {
    Payment processPayment(User user) throws Exception;
    Payment getPaymentDetails(UUID paymentId,User user) throws Exception;
//get all payments can be fetched only the admin
    List<Payment> getAllPayments();
    Payment updatePaymentStatus(UUID paymentId, String transactionId, PaymentStatus paymentStatus,User user) throws Exception;


}
