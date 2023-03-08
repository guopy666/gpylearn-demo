package com.gpy.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.security.Key;

/**
 * DESede对称加密算法演示
 * 
 * @author zolly
 * */
public class DESedeCoder {

    /**
     * 密钥算法
     * */
    public static final String KEY_ALGORITHM = "DESede";

    /**
     * 加密/解密算法/工作模式/填充方式
     * */
    public static final String CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";
    
    /**
     * 
     * 生成密钥
     * 
     * @return byte[] 二进制密钥
     * */
    public static byte[] initkey() throws Exception {

        // 实例化密钥生成器
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        // 初始化密钥生成器
        kg.init(168);
        // 生成密钥
        SecretKey secretKey = kg.generateKey();
        // 获取二进制密钥编码形式

        byte[] key = secretKey.getEncoded();
        BufferedOutputStream keystream = 
                new BufferedOutputStream(new FileOutputStream("DESedeKey.dat"));
        keystream.write(key, 0, key.length);
        keystream.flush();
        keystream.close();

        return key;
    }

    /**
     * 转换密钥
     * 
     * @param key
     *            二进制密钥
     * @return Key 密钥
     * */
    public static Key toKey(byte[] key) throws Exception {
        // 实例化Des密钥
        DESedeKeySpec dks = new DESedeKeySpec(key);
        // 实例化密钥工厂
        SecretKeyFactory keyFactory = SecretKeyFactory
                .getInstance(KEY_ALGORITHM);
        // 生成密钥
        SecretKey secretKey = keyFactory.generateSecret(dks);
        return secretKey;
    }

    /**
     * 加密数据
     * 
     * @param data
     *            待加密数据
     * @param key
     *            密钥
     * @return byte[] 加密后的数据
     * */
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 还原密钥
        Key k = toKey(key);
        // 实例化
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        // 初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, k);
        // 执行操作
        return cipher.doFinal(data);
    }

    /**
     * 解密数据
     * 
     * @param data
     *            待解密数据
     * @param key
     *            密钥
     * @return byte[] 解密后的数据
     * */
    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 欢迎密钥
        Key k = toKey(key);
        // 实例化
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        // 初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);
        // 执行操作
        return cipher.doFinal(data);
    }

    /**
     * 加密字符串
     * @param data
     * @return
     * @throws Exception 
     */
    public static String encode(String str,String screatKey) throws Exception{
        String result = "";
        byte[] data = DESedeCoder.encrypt(str.getBytes(), screatKey.getBytes());
        result =  Base64.encode(data);
        return result;
    }

    /**
     * 解密字符串
     * @param str
     * @return
     */
    public static String decode(String str,String screatKey){
        String result = "";
        try {
            byte[] data = Base64.decode(str);
            data = DESedeCoder.decrypt(data, screatKey.getBytes());
            result = new String(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 进行加解密的测试
     * 
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String SECRET_KEY = "GDgLwwdK270Qj1w4xho8lyTp";
        String crypto = encode("123456哈哈 你好！", SECRET_KEY);
    	System.out.println(crypto);
    	System.out.println(decode(crypto, SECRET_KEY));
    	/*
    	String secret_key = "JYO2O01";
	    
	    String before_pwd_prefix = "jycard";
	    String after_pwd_prefix = "jycoin";        
    	String customid = "MDAwMDI0NzQO0O0O";		
		String cardNum = "2336379940802937368";		
		String mobile = "13680000000";
		String pwd = "888888";		
		pwd = MD5Util.md5(pwd);
		pwd = before_pwd_prefix+pwd+after_pwd_prefix;
		String key = MD5Util.md5(secret_key+mobile+pwd);
		String str = "{" +
				"\"mobile\":\""+mobile+"\"," +
				"\"pwd\":\""+pwd+"\"," +
				"\"key\":\""+key+"\"" +
				"}";
        System.out.println("Key:"+SECRET_KEY);
        System.out.println("原文：" + str);
        //加密
        String value = encode(str,SECRET_KEY);
        System.out.println("加密后：" + value);
        System.out.println("解密后：" + decode(value,SECRET_KEY));
        */
    }
}
