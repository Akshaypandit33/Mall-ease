package com.akshay.backend.Controller.Client;

import com.akshay.backend.Controller.Client.Request.UpdatePaymentStatusRequest;
import com.akshay.backend.Model.Payment;
import com.akshay.backend.Model.User;
import com.akshay.backend.Repository.PaymentRepository;
import com.akshay.backend.Service.PaymentService;
import com.akshay.backend.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;
    private final UserService userService;
    private final PaymentRepository paymentRepository;

    @PostMapping("/process")
    public Payment processPayment(@RequestHeader("Authorization") String token) throws Exception {
        User user= userService.getUserFromToken(token);
        return paymentService.processPayment(user);
    }

    @PostMapping("/getDetails/{paymentId}")
    public Payment getPaymentDetails(@RequestHeader("Authorization") String token, @PathVariable UUID paymentId) throws Exception {
        User user= userService.getUserFromToken(token);
        return paymentService.getPaymentDetails(paymentId,user);
    }


    @PostMapping("/getAll")
    public List<Payment> getAllPayments()
    {
        return paymentService.getAllPayments();
    }

    @PostMapping("/update")
    public Payment updatePaymentStatus(@RequestHeader("Authorization") String token, @RequestBody UpdatePaymentStatusRequest request) throws Exception {
        User user= userService.getUserFromToken(token);

        return paymentService.updatePaymentStatus(request.getPaymentId(),request.getTransactionId(),request.getPaymentStatus(),user);
    }
}
