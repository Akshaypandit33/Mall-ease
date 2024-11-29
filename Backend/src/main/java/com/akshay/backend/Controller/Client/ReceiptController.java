package com.akshay.backend.Controller.Client;

import com.akshay.backend.Model.Receipt;
import com.akshay.backend.Model.User;
import com.akshay.backend.Service.ReceiptService;
import com.akshay.backend.Service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Data
@RequiredArgsConstructor
@RequestMapping("/receipt")
public class ReceiptController {

    private final ReceiptService receiptService;
    private final UserService userService;

    @PostMapping("/generate/{paymentId}")
    public Receipt generateReceipt(@PathVariable UUID paymentId, @RequestHeader("Authorization") String token) throws Exception {
        User user= userService.getUserFromToken(token);
        return receiptService.generateReceipt(paymentId,user);

    }

    @PostMapping("/get/{receiptId}")
    public Receipt getReceipt(@PathVariable UUID receiptId) throws Exception {
        return receiptService.getReceipt(receiptId);
    }


//    receipt can be verified and accesed by only admin or security guard
    @PostMapping("/verify/{receiptId}")
    public Receipt VerifyReceipt(@PathVariable UUID receiptId, @RequestHeader("Authorization") String token) throws Exception {
        User user =userService.getUserFromToken(token);
        return receiptService.verfifyReceipt(receiptId,user);
    }
}
