package com.gpy.image;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageTest {
    public static void main(String[] args) throws Exception {
        String text = "Hello, World!";
        String logoUrl = "http://www.yijiahn.com/static/img/logo1.png";
        int width = 300;
        int height = 300;
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        BufferedImage circleQRCode = makeCircle(qrCodeImage);
        ImageIO.write(circleQRCode, "PNG", new File("circle_qrcode.png"));

        QRCodeWriter qrCodeWriter3 = new QRCodeWriter();
        BitMatrix bitMatrix3 = qrCodeWriter3.encode(text, BarcodeFormat.QR_CODE, width, height);
        BufferedImage qrCodeImage3 = MatrixToImageWriter.toBufferedImage(bitMatrix3);
        BufferedImage circleQRCode3 = makeCircle2(qrCodeImage3);
        ImageIO.write(circleQRCode3, "PNG", new File("circle_qrcode2222.png"));

        QRCodeWriter qrCodeWriter2 = new QRCodeWriter();
        BitMatrix matrix = qrCodeWriter2.encode(logoUrl, BarcodeFormat.QR_CODE, width, height);
        BufferedImage qrCodeImage2 = MatrixToImageWriter.toBufferedImage(matrix);
        BufferedImage circleLogo = addCircleLogo(qrCodeImage2, 10, 10, width, height, qrCodeImage);
        ImageIO.write(circleLogo, "PNG", new File("circle_222.png"));

    }

    private static BufferedImage makeCircle(BufferedImage source) {
        int width = source.getWidth();
        int height = source.getHeight();
        BufferedImage output = new BufferedImage(width * 2, height * 2, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = output.createGraphics();

        // 设置抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int border = 1;

        // Create an elliptical shape for the new image
        Ellipse2D.Double shape = new Ellipse2D.Double(border, border, width - border * 2, height - height * 2);

        // Create a new image of the same size as the original one
        BufferedImage mask = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2dMask = mask.createGraphics();

        // Paint the shape to the mask
        g2dMask.setColor(Color.WHITE);
        g2dMask.fill(shape);

        // Use the mask to "cut out" the circular part from the original image
        // g2d.setClip(mask);
        // g2d.drawImage(source, 0, 0, null);
        // g2d.dispose();
        // g2dMask.dispose();
        g2d.setClip(shape);
        // 在圆形区域绘制 logo
        g2d.drawImage(source, border, border, width - border * 2, width - border * 2, null);
        g2d.dispose();

        return output;
    }

    private static BufferedImage makeCircle2(BufferedImage source) throws IOException {
        int width = source.getWidth();
        int height = source.getHeight();
        BufferedImage output = new BufferedImage(width * 2, height * 2, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = output.createGraphics();

        // 设置抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int border = 1;

        // Create an elliptical shape for the new image
        Ellipse2D.Double shape = new Ellipse2D.Double(border, border, width - border * 2, height - height * 2);

        // Create a new image of the same size as the original one
        BufferedImage mask = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2dMask = mask.createGraphics();

        // Paint the shape to the mask
        g2dMask.setColor(Color.WHITE);
        g2dMask.fill(shape);

        // Use the mask to "cut out" the circular part from the original image
        // g2d.setClip(mask);
        // g2d.drawImage(source, 0, 0, null);
        // g2d.dispose();
        // g2dMask.dispose();
        g2d.setClip(shape);
        // 在圆形区域绘制 logo
        g2d.drawImage(convertCircular(source), border, border, width - border * 2, width - border * 2, null);
        g2d.dispose();

        return output;
    }

    public static BufferedImage convertCircular(BufferedImage bi1) throws IOException {
        //这种是黑色底的
        // BufferedImage bi2 = new BufferedImage(bi1.getWidth(),bi1.getHeight(),BufferedImage.TYPE_INT_RGB);

        //透明底的图片
        BufferedImage bi2 = new BufferedImage(bi1.getWidth(),bi1.getHeight(),BufferedImage.TYPE_4BYTE_ABGR);
        Ellipse2D.Double shape = new Ellipse2D.Double(0,0,bi1.getWidth(),bi1.getHeight());
        Graphics2D g2 = bi2.createGraphics();
        g2.setClip(shape);
        // 使用 setRenderingHint 设置抗锯齿
        g2.drawImage(bi1,0,0,null);
        //设置颜色
        g2.setBackground(Color.green);
        g2.dispose();
        return bi2;
    }


    private static BufferedImage addCircleLogo(BufferedImage logoImage, int leftBorder, int topBorder, int width, int height, BufferedImage bgImage) throws Exception {

        //  透明底的图片
        BufferedImage formatAvatarImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        // 获取透明画布对象
        Graphics2D graphics = formatAvatarImage.createGraphics();
        // 设置抗锯齿
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 留一个像素的空白区域，这个很重要，画圆的时候把这个覆盖
        int border = 1;

        // 设置圆形裁剪区
        Ellipse2D.Double shape = new Ellipse2D.Double(border, border, width - border * 2, width*1.5 - border * 2);
        graphics.setClip(shape);
        // 在圆形区域绘制 logo
        graphics.drawImage(logoImage, border, border, width - border * 2, width - border * 2, null);
        graphics.dispose();

        // 在圆图外面再画一个圆
        // 新创建一个graphics，这样画的圆不会有锯齿
        graphics = formatAvatarImage.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int outBorder = 3;
        // 画笔是4.5个像素，BasicStroke的使用可以查看下面的参考文档
        // 使画笔时基本会像外延伸一定像素，具体可以自己使用的时候测试
        Stroke s = new BasicStroke(4.5F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        graphics.setStroke(s);
        graphics.setColor(Color.WHITE);
        graphics.drawOval(outBorder, outBorder, width - outBorder * 2, width - outBorder * 2);
        graphics.dispose();

        // 开始绘制背景
        graphics = bgImage.createGraphics();
        graphics.drawImage(bgImage, 0, 0, null);

        // 将空白画布绘制到背景上
        int x = (bgImage.getWidth() - width) / 2;
        int y = (bgImage.getHeight() - width) / 2;
        graphics.drawImage(formatAvatarImage, leftBorder, topBorder, width, width, null);
        graphics.dispose();
        return formatAvatarImage;
    }



}