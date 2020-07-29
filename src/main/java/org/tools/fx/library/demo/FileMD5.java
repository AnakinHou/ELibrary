package org.tools.fx.library.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//JAVA中获取文件MD5值的四种方法其实都很类似，因为核心都是通过JAVA自带的MessageDigest类来实现。
//获取文件MD5值主要分为三个步骤，第一步获取文件的byte信息，第二步通过MessageDigest类进行MD5加密，
//第三步转换成16进制的MD5码值。几种方法的不同点主要在第一步和第三步上。具体可以看下面的例子：
//org.apache.commons.io.FileUtils.readFileToByteArray() 
public class FileMD5 {
  
  private final static String[] strHex = { "0", "1", "2", "3", "4", "5",
      "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

 
//  方法一是比较原始的一种实现方法，首先将文件一次性读入内存，然后通过MessageDigest进行MD5加密，最后再手动将其转换为16进制的MD5值。
//public static String getMD5One(String path) {
//  StringBuffer sb = new StringBuffer();
//  try {
//      MessageDigest md = MessageDigest.getInstance("MD5");
//      byte[] b = md.digest(FileUtils.readFileToByteArray(new File(path)));
//      for (int i = 0; i < b.length; i++) {
//          int d = b[i];
//          if (d < 0) {
//              d += 256;
//          }
//          int d1 = d / 16;
//          int d2 = d % 16;
//          sb.append(strHex[d1] + strHex[d2]);
//      }
//  } catch (NoSuchAlgorithmException e) {
//      e.printStackTrace();
//  } catch (IOException e) {
//    e.printStackTrace();
//  }
//  return sb.toString();
//}

//public static String getMD5Two(String path) {
//  StringBuffer sb = new StringBuffer("");
//  try {
//      MessageDigest md = MessageDigest.getInstance("MD5");
//      md.update(FileUtils.readFileToByteArray(new File(path)));
//      byte b[] = md.digest();
//      int d;
//      for (int i = 0; i < b.length; i++) {
//          d = b[i];
//          if (d < 0) {
//              d = b[i] & 0xff;
//              // 与上一行效果等同
//              // i += 256;
//          }
//          if (d < 16)
//              sb.append("0");
//          sb.append(Integer.toHexString(d));
//      }
//  } catch (NoSuchAlgorithmException e) {
//      e.printStackTrace();
//  } catch (IOException e) {
//      e.printStackTrace();
//  }
//  return sb.toString();
//}

//方法二与方法一不同的地方主要是在步骤三，这里借助了Integer类的方法实现16进制的转换，比方法一更简洁一些。
//PS：JAVA中byte是有负数的，代码中&0xff的操作与计算机中数据存储的原理有关，
//即负数存储的是二进制的补码，有兴趣的童鞋可以挖一下，这里不展开说。




public static String getMD5Three(String path) {
  BigInteger bi = null;
  try {
    File f = new File(path);
    if(!f.exists()) {
      return null;
    }
      byte[] buffer = new byte[8192];
      int len = 0;
      MessageDigest md = MessageDigest.getInstance("MD5");
      FileInputStream fis = new FileInputStream(f);
      while ((len = fis.read(buffer)) != -1) {
          md.update(buffer, 0, len);
      }
      fis.close();
      byte[] b = md.digest();
      bi = new BigInteger(1, b);
  } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
  } catch (IOException e) {
      e.printStackTrace();
  }
  return bi.toString(16);
}

//方法三与前面两个方法相比，在读入文件信息上有点不同。这里是分多次将一个文件读入，对于大型文件而言，
//比较推荐这种方式，占用内存比较少。步骤三则是通过BigInteger类提供的方法进行16进制的转换，与方法二类似。

//方法四、
//
//1  DigestUtils.md5Hex(new FileInputStream(path));
//方法四应该是最便捷的吧，哈哈，好东西要留在最后，如果你只需要使用标准的MD5，其实一行代码就够了，
//JAVA自带的commons-codec包就提供了获取16进制MD5值的方法。其底层实现上，也是分多次将一个文件读入，类似方法三。所以性能上也不错。



//总结：其实方法都是类似的，推荐使用方法四，简洁且性能不错，当然，如果要做一些调整什么的，可以根据自己的需求进行方法的选择。
//PS：其实还有一个重点，就是如何知道自己生成的MD5值是否正确呢？
//方法很多，其实有一个挺简单的方法，不需要另外安装什么软件。使用windows自带的命令即可：certutil -hashfile [文件路径] MD5，例子如下：

public static void main(String[] args) {
 String md5Value = getMD5Three("/Users/hydra/MyStudy/Maven实战.pdf");
 System.out.println(md5Value);
 String md5Value2 = getMD5Three("/Users/hydra/MyStudy/Maven实战的副本.pdf");
 System.out.println(md5Value2);
}
}
