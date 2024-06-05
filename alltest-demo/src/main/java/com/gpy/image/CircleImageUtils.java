package com.gpy.image;

/**
 * @ClassName CircleImageUtils
 * @Description
 * @Author guopy
 * @Date 2024/6/4 17:01
 */

import com.swetake.util.Qrcode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 *  二维码生成工具类
 */
public class CircleImageUtils {
    //生成二维码图片
    public static void getQrcode(String content,String imgPath) throws IOException {

        int width=140;
        int height=140;
        //创建二维码
        Qrcode  qrcode=new Qrcode();
        //设置二维码的纠错值
        qrcode.setQrcodeErrorCorrect('M');
        //设置二维码编码方式
        qrcode.setQrcodeEncodeMode('B');
        //设置二维码的版本，也叫信息容量
        qrcode.setQrcodeVersion(7);
        System.out.println(content);
        //将信息转化为字节数组
        byte[] contentByte=content.getBytes("gb2312");
        //相当于创建一个画布
        BufferedImage bufferedImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);
        //得到画笔
        Graphics2D gs=bufferedImage.createGraphics();
        //设置背景颜色
        gs.setBackground(Color.white);
        //画出一个矩形区域
        gs.clearRect(0,0,width,height);
        //设置图形颜色
        gs.setColor(Color.red);
        // 设置偏移量 不设置可能导致解析出错
        int pixoff = 2;
        //输出二维码图片
        if(contentByte.length>0 && contentByte.length<120){
            boolean[][] codeOut=qrcode.calQrcode(contentByte);
            //循环输出，形成矩阵
            for(int i=0;i<codeOut.length;i++){
                for(int j=0;j<codeOut.length;j++){
                    if(codeOut[j][i]){  //如果方阵下面有内容就画出一个小矩形
                        gs.fillRect(j*3+pixoff,i*3+pixoff,3,3);
                    }
                }
            }
        } else {
            System.err.println("QRCode content bytes length = " + contentByte.length + " not in [ 0,120 ]." );
        }

        gs.dispose();
        bufferedImage.flush();
        File file=new File(imgPath);
        //生产二维码图片进行输出
        ImageIO.write(bufferedImage,"png",file);
    }
    //生成带有logo的二维码图片
    public static void getLogoQrcode(String content,String imgPath,int width,int height,String logoPath) throws IOException {
        //创建二维码
        Qrcode  qrcode=new Qrcode();
        //设置二维码的纠错值
        qrcode.setQrcodeErrorCorrect('M');
        //设置二维码编码方式
        qrcode.setQrcodeEncodeMode('B');
        //设置二维码的版本，也叫信息容量
        qrcode.setQrcodeVersion(7);
        System.out.println(content);
        //将信息转化为字节数组
        byte[] contentByte=content.getBytes("gb2312");
        //相当于创建一个画布
        BufferedImage bufferedImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);
        //得到画笔
        Graphics2D gs=bufferedImage.createGraphics();
        //设置背景颜色
        gs.setBackground(Color.white);
        //画出一个矩形区域
        gs.clearRect(0,0,width,height);
        //设置图形颜色
        gs.setColor(Color.black);
        // 设置偏移量 不设置可能导致解析出错
        int pixoff = 2;
        //输出二维码图片
        if(contentByte.length>0 && contentByte.length<120){
            boolean[][] codeOut=qrcode.calQrcode(contentByte);
            //循环输出，形成矩阵
            for(int i=0;i<codeOut.length;i++){
                for(int j=0;j<codeOut.length;j++){
                    if(codeOut[j][i]){  //如果方阵下面有内容就画出一个小矩形
                        gs.fillRect(j*3+pixoff,i*3+pixoff,3,3);
                    }
                }
            }
        } else {
            System.err.println("QRCode content bytes length = " + contentByte.length + " not in [ 0,120 ]." );
        }
        //绘制logo图像
        BufferedImage img = ImageIO.read(new URL(logoPath));
        //Image img=ImageIO.read(new File(logoPath));
        gs.drawImage(convertCircular(img),50,50,40,40,null);
        gs.dispose();
        bufferedImage.flush();
        File file=new File(imgPath);
        //生产二维码图片进行输出
        ImageIO.write(bufferedImage,"png",file);
    }
    /**
     * 传入的图像必须是正方形的 才会 圆形  如果是长方形的比例则会变成椭圆的
     * @param bi1 用户头像地址
     * @return
     * @throws IOException
     */
    public static BufferedImage convertCircular(BufferedImage bi1) throws IOException{
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
    public static void fixqrcode(String content,String imgPath,String logoPath) throws Exception{
        int width=140;
        int height=140;
        getLogoQrcode(content,imgPath,width,height,logoPath);
    }
    public static void main(String[] args) throws Exception{
        //输入二维码的信息
        String content="这是二维码内容";
        //存放二维码的地址
        String filePath = "333333.png";
        //产生不带有logo的二维码
        getQrcode(content,filePath);
        //logo图片的地址
        String logoPath="http://www.yijiahn.com/static/img/logo1.png";
        String imgLogoPath="444444.png";
        //生成带有logo的二维码
        fixqrcode(content,imgLogoPath,logoPath);
        System.out.println("it's great,success");
    }
}


