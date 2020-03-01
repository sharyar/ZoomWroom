/**
 * @author Dulong Sang
 */

/*
 * Use examples:
 * QRCode qrCode = new QRCode("content here");
 * ImageView imageView = findViewById(R.id.qr_code);
 * qrCode.setToImageView(imageView);
 * same as: imageView.setImageBitmap(qrCode.getBitmap());
 *
 * You can also set the QR code to an ImageView without creating a QRCode instance:
 * QRCode.setQRCodeToImageView("content here", imageView);
 *
 * You can specify the QR code size if you want, which is default to 500.
 * (Not recommended, ImageView adjust the size automatically.)
 */

package com.example.zoomwroom.Entities;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class QRCode {
    public static int defaultSize = 500;

    private String content;
    private int size = defaultSize;
    private Bitmap bitmap;

    public QRCode(String content) {
        this.content = content;
        this.bitmap = generateQRCodeBitmap(content, size);
    }

    public QRCode(String content, int size) {
        this.content = content;
        this.size = size;
        this.bitmap = generateQRCodeBitmap(content, size);
    }

    public String getContent() {
        return content;
    }

    public int getSize() {
        return size;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setToImageView(ImageView imageView) {
        if (bitmap == null) {
            System.out.println("Error: generateQRCode encountered a WriterException.");
            return;
        }
        imageView.setImageBitmap(bitmap);
    }

    /**
     * reference: https://stackoverflow.com/questions/46065310/how-to-create-a-qr-code-generator-for-android-using-fragments
     *
     * @param content   the content to be stored in the QR code
     * @param size      the QR code will be size x size
     * @return          returns the Bitmap of the generated QR code
     */
    public static Bitmap generateQRCodeBitmap(String content, int size) {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, size, size);
        } catch (IllegalArgumentException | WriterException e) {
            System.out.println("Error: generateQRCode encountered a WriterException.");
            return null;
        }

        int[] pixels = new int[size * size];
        int offset;
        // ARGB colors
        int colorWhite = 0xFFFFFFFF;
        int colorBlack = 0xFF000000;

        // fill with color black and white
        for (int y = 0; y < size; y++) {
            offset = y * size;
            for (int x = 0; x < size; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ? colorBlack : colorWhite;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, size, 0, 0, size, size);

        return bitmap;
    }

    public static void setQRCodeToImageView(String content, ImageView imageView) {
        imageView.setImageBitmap(generateQRCodeBitmap(content, defaultSize));
    }
}
