package com.akshay.backend.Service;

import com.google.zxing.WriterException;

import java.io.IOException;
import java.util.UUID;

public interface QRCodeService {

    void generateQRCodeImage(UUID receiptId, int width, int height ) throws WriterException, IOException;
    public byte[] getQRCodeImage(UUID receiptId, int width, int height) throws Exception;

}
