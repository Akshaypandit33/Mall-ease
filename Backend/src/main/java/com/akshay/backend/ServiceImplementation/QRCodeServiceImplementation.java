package com.akshay.backend.ServiceImplementation;

import com.akshay.backend.Service.QRCodeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class QRCodeServiceImplementation implements QRCodeService {

    public static final String QR_CODE_IMAGE_PATH= "./src/main/resources/static/QrCode/";

    @Override
    public void generateQRCodeImage(UUID receiptId, int width, int height) throws WriterException, IOException {
        Map<EncodeHintType, ErrorCorrectionLevel> hints =new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        BitMatrix bitMatrix =new MultiFormatWriter().encode(receiptId.toString(), BarcodeFormat.QR_CODE, width, height,hints);
        // Create the directory if it doesn't exist
        File directory = new File(QR_CODE_IMAGE_PATH);
        if (!directory.exists()) {
            directory.mkdirs();  // Create the directory
        }
//        Path path = FileSystems.getDefault().getPath(QR_CODE_IMAGE_PATH);
        Path path = FileSystems.getDefault().getPath(QR_CODE_IMAGE_PATH + receiptId.toString() + ".png");


        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    @Override
    public byte[] getQRCodeImage(UUID receiptId, int width, int height) throws Exception {
        Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        BitMatrix bitMatrix = new MultiFormatWriter().encode(
                receiptId.toString(),
                BarcodeFormat.QR_CODE, width, height, hints);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return pngOutputStream.toByteArray();
    }
}
