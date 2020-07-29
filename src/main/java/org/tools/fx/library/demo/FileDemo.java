package org.tools.fx.library.demo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class FileDemo {
  public static void main(String[] args) {
    File f = new File("/Users/hydra/MyStudy/Maven实战.pdf");
   System.out.println("getAbsoluteFile:"+ f.getAbsoluteFile() );
   System.out.println("getAbsolutePath:"+ f.getAbsolutePath());
   try {
     System.out.println("getCanonicalFile:"+ f.getCanonicalFile());
    System.out.println("getCanonicalPath:"+ f.getCanonicalPath());
  } catch (IOException e) {
    e.printStackTrace();
  }
   System.out.println("getFreeSpace:"+ f.getFreeSpace());
   System.out.println("getName:"+ f.getName());
   System.out.println("getParent:"+  f.getParent());
   System.out.println("getParentFile:"+ f.getParentFile());
   System.out.println("getPath:"+ f.getPath());
   System.out.println("getTotalSpace:"+  f.getTotalSpace());
   System.out.println("getUsableSpace:"+ f.getUsableSpace());
   System.out.println("canExecute:"+  f.canExecute());
   System.out.println("canRead:"+  f.canRead());
   System.out.println("canWrite:"+  f.canWrite());
   System.out.println("isAbsolute:"+ f.isAbsolute());
   System.out.println("isDirectory:"+  f.isDirectory());
   System.out.println("isFile:"+  f.isFile());
   System.out.println("isHidden:"+  f.isHidden());
   System.out.println("length:"+  f.length());
   System.out.println("lastModified:"+  new Date(f.lastModified()));
    

    try {
      BasicFileAttributes attributes = Files.readAttributes(new File("/Users/hydra/MyStudy/Maven实战.pdf").toPath(), BasicFileAttributes.class);
      LocalDateTime fileCreationTime = LocalDateTime.ofInstant(attributes.creationTime().toInstant(), ZoneId.systemDefault());
      LocalDateTime fileLastModifiedTime = LocalDateTime.ofInstant(attributes.lastModifiedTime().toInstant(), ZoneId.systemDefault());
      System.out.println(fileCreationTime);
      System.out.println(fileLastModifiedTime);
    } catch (IOException e) {
      e.printStackTrace();
    }
    

    

  }

}
