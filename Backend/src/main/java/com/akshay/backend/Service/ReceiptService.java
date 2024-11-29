package com.akshay.backend.Service;

import com.akshay.backend.Model.Receipt;
import com.akshay.backend.Model.ReceiptStatus;
import com.akshay.backend.Model.User;

import java.util.UUID;

public interface ReceiptService {

    Receipt generateReceipt(UUID paymentId, User user) throws Exception;

    Receipt getReceipt(UUID receiptId) throws Exception;

//    receipt can be verified by only security guard or admin not by customer
    Receipt verfifyReceipt(UUID receiptId, User user) throws Exception;
}
