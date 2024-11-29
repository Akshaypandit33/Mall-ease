package com.akshay.backend.ServiceImplementation;

import com.akshay.backend.Model.*;
import com.akshay.backend.Repository.ReceiptRepository;
import com.akshay.backend.Service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReceiptServiceImplementation implements ReceiptService {

    private final PaymentService paymentService;
    private final QRCodeService qrCodeService;
    private final ReceiptRepository receiptRepository;
    @Override
    public Receipt generateReceipt(UUID paymentId, User user) throws Exception {

        Payment payment= paymentService.getPaymentDetails(paymentId,user);
        Receipt newReceipt= new Receipt();
        newReceipt.setReceipt_id(UUID.randomUUID());
        newReceipt.setPayment(payment);
        newReceipt.setReceiptStatus(ReceiptStatus.UNCHECKED);
        newReceipt.setGenerationDate(LocalDateTime.now());
        newReceipt.setExpirationTime(LocalDateTime.now().plusHours(3));

        byte[] qrcode= qrCodeService.getQRCodeImage(newReceipt.getReceipt_id(),250,250);
        newReceipt.setQr_code(qrcode);
//        newReceipt.setQr_code(QRCodeServiceImplementation.QR_CODE_IMAGE_PATH+ newReceipt.getReceipt_id()+ ".png");
        return receiptRepository.save(newReceipt);
    }


    @Override
    public Receipt getReceipt(UUID receiptId) throws Exception {

        return receiptRepository.findById(receiptId).orElseThrow(() -> new Exception("Receipt not found"));
    }

    @Override
    public Receipt verfifyReceipt(UUID receiptId, User user) throws Exception {
        Receipt receipt=getReceipt(receiptId);
        if(user.getRole().equals(Role.ADMIN) || user.getRole().equals(Role.SECURITY))
        {
            receipt.setReceiptStatus(ReceiptStatus.CHECKED);
            if(receipt.getGenerationDate().isBefore(receipt.getExpirationTime()))
            {
                receipt.setQr_code(null);
                receiptRepository.save(receipt);
                return receipt;
            }
        }
        return null;
    }


}
