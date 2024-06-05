package com.gpy.image;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QrCodeUtils {

    public static void main(String[] args) {
        String text = "https://www.example.com";
        int size = 300;
        String filePath = "circular_qrcode.png";

        try {
            generateCircularQRCode(text, size, filePath);
            System.out.println("===========");
            generateCircleImage();
            System.out.println("------------");
            circleBg(text, size);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateCircularQRCode(String text, int size, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, size, size, hints);
        BufferedImage qrCodeImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        qrCodeImage.createGraphics();

        Graphics2D graphics = (Graphics2D) qrCodeImage.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, size, size);
        graphics.setColor(Color.BLACK);

        for (int i = 0; i< size; i++) {
            for (int j = 0; j< size; j++) {
                if (bitMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }

        BufferedImage circularImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = circularImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int border = 1;
        // 设置圆形裁剪区
        Ellipse2D.Double shape = new Ellipse2D.Double(border, border, size - border * 2, size - border * 2);
        g2.setClip(shape);
        g2.drawImage(qrCodeImage, 0, 0, null);
        g2.dispose();

        ImageIO.write(circularImage, "png", new File(filePath));
    }

    private static void generateCircleImage() throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        String text = "这是内容";
        Integer size = 300;
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, size, size, hints);

        BufferedImage qrCodeImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics = (Graphics2D) qrCodeImage.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, size, size);
        graphics.setColor(Color.BLACK);

        for (int i = 0; i< size; i++) {
            for (int j = 0; j< size; j++) {
                if (bitMatrix.get(i, j)) {
                    qrCodeImage.setRGB(i, j, Color.black.getRGB());
                }
            }
        }
        graphics.drawOval(0, 0, size, size);
        graphics.dispose();

        File outputFile = new File("9999.png");
        MatrixToImageWriter.writeToPath(bitMatrix, "png", outputFile.toPath());
    }

    private static void circleBg(String content, Integer size) throws IOException, WriterException {

        // 输出图片路径
        String outputImagePath = "56666666.png";

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, size, size, hints);
        BufferedImage qrCodeImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);

        // 创建一个新的图片，宽度和高度为原图的1.5倍，并指定颜色类型和alpha通道
        BufferedImage newImage = new BufferedImage(
                qrCodeImage.getWidth() * 2,
                qrCodeImage.getHeight() * 2,
                BufferedImage.TYPE_INT_ARGB);

        // 创建一个Graphics2D对象，用于绘制
        Graphics2D g2d = newImage.createGraphics();

        // 设置背景为透明
        newImage = g2d.getDeviceConfiguration().createCompatibleImage(
                qrCodeImage.getWidth() * 2,
                qrCodeImage.getHeight() * 2,
                Transparency.TRANSLUCENT);

        // 绘制圆形背景
        Ellipse2D.Double e = new Ellipse2D.Double(0, 0, newImage.getWidth(), newImage.getHeight());
        g2d.setColor(Color.WHITE); // 设置背景颜色为白色
        g2d.fill(e); // 填充圆形

        // 绘制原始图片
        g2d.drawImage(qrCodeImage, newImage.getWidth() / 4, newImage.getHeight() / 4,
                qrCodeImage.getWidth(), qrCodeImage.getHeight(), null);

        // 释放画笔资源
        g2d.dispose();

        // 输出新图片到文件
        ImageIO.write(newImage, "PNG", new File(outputImagePath));
    }


}