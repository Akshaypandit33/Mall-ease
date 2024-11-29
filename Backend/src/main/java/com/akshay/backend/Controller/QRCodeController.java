package com.akshay.backend.Controller;

import com.akshay.backend.Service.QRCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/QrCode")
public class QRCodeController {

    private final QRCodeService qrCodeService;

    @PostMapping("/generate/{receiptId}")
    public ResponseEntity<byte[]> generateQrCode(@PathVariable UUID receiptId) throws Exception {
        try{
            byte[] qrImage=qrCodeService.getQRCodeImage(receiptId,250,250);
            qrCodeService.generateQRCodeImage(receiptId,250,250);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);

            return new ResponseEntity<>(qrImage, headers, HttpStatus.OK);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
