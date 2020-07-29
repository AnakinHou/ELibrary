package org.tools.fx.library.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encode {

    /**
     * sha256加密
     *
     * @param text 内容
     * @return digest 摘要
     * @throws NoSuchAlgorithmException e <br>
     *         <dependency> <groupId>commons-codec</groupId> <artifactId>commons-codec</artifactId>
     *         <version>${common-codec.version}</version> </dependency>
     */
    // public static String sha256(String text) throws NoSuchAlgorithmException {
    // MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
    // byte[] bytes = messageDigest.digest(text.getBytes());
    // return Hex.encodeHexString(bytes);
    // }

    /***
     * 利用Apache的工具类实现SHA-256加密
     * 
     * @param str 加密后的报文
     * @return
     */
    // public static String getSHA256Str(String str) {
    // MessageDigest messageDigest;
    // String encdeStr = "";
    // try {
    // messageDigest = MessageDigest.getInstance("SHA-256");
    // byte[] hash = messageDigest.digest(str.getBytes("UTF-8"));
    // encdeStr = Hex.encodeHexString(hash);
    // } catch (NoSuchAlgorithmException e) {
    // e.printStackTrace();
    // } catch (UnsupportedEncodingException e) {
    // e.printStackTrace();
    // }
    // return encdeStr;
    // }

    /**
     * 利用java原生的摘要实现SHA256加密
     * 
     * @param str 加密后的报文
     * @return
     */
    public static String getSHA256Str(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            String encodeStr = byte2Hex(messageDigest.digest());
            // System.out.println(encodeStr);
            return encodeStr;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将byte转为16进制
     * 
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                // 1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    /**
     * MD5，Message Digest Algorithm 5，是一种被广泛使用的信息摘要算法，可以将给定的任意长度数据通过一定的算法计算得出一个128位二进制的散列值。
     * 常见的表示方法是将128位二进制转成32位16进制，这样看起来比较简短。 方法1 这种方法要注意一点的是不足32位高位需补零，否则会不足位，比如：
     * 6531经MD5计算后正确的结果为0a7d83f084ec258aefd128569dda03d7
     * 用方法1如果不高位补零返回的结果为a7d83f084ec258aefd128569dda03d7，前面的0少了
     * 
     * @param input
     * @return
     */
    private static String MD51(String input) {
        if (input == null || input.length() == 0) {
            return null;
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(input.getBytes());
            byte[] byteArray = md5.digest();

            BigInteger bigInt = new BigInteger(1, byteArray);
            // 参数16表示16进制
            String result = bigInt.toString(16);
            // 不足32位高位补零
            while (result.length() < 32) {
                result = "0" + result;
            }
            return result.toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String MD52(String input) {
        if (input == null || input.length() == 0) {
            return null;
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(input.getBytes());
            byte[] byteArray = md5.digest();

            char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c',
                    'd', 'e', 'f'};
            // 一个字节对应两个16进制数，所以长度为字节数组乘2
            char[] charArray = new char[byteArray.length * 2];
            int index = 0;
            for (byte b : byteArray) {
                charArray[index++] = hexDigits[b >>> 4 & 0xf];
                charArray[index++] = hexDigits[b & 0xf];
            }
            return new String(charArray);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String MD53(String input) {
        if (input == null || input.length() == 0) {
            return null;
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(input.getBytes());
            byte[] byteArray = md5.digest();

            StringBuilder sb = new StringBuilder();
            for (byte b : byteArray) {
                // 一个byte格式化成两位的16进制，不足两位高位补零
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 计算文件的MD5值
     * 
     * 任何文件都可以计算MD5值，因为任何文件实际上就是字节数组
     * 
     * @param path
     * @return
     */
    private static String fileToMD5(String path) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            FileInputStream fis = new FileInputStream(path);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                md5.update(buffer, 0, len);
            }
            fis.close();

            byte[] byteArray = md5.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : byteArray) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String MD5(String input) {
        return MD51(input);
    }

    public static void main(String[] args) {
        System.out.println("======== " + getSHA256Str("password"));
        String str = "Elements 10B8" + "575842314132345332303635" + "204800";
        // 03632D64-5FC3-490D-84A1-028FD8A3A13F
        System.out.println("======== " + MD51(str));
        System.out.println("======== " + MD52(str));
        System.out.println("======== " + MD53(str));
    }

}
