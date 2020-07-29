package org.tools.fx.library.device;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

import javax.swing.filechooser.FileSystemView;

public class InfoTest {

	public static void getDiskInfo() {
		FileSystemView fileSys=FileSystemView.getFileSystemView(); //获取当前系统文bai件类型
		File[] disks = File.listRoots();
		for (File file : disks) {
			System.out.println(fileSys.getSystemDisplayName(file));//获取系统卷标及名字

			System.out.println(fileSys.getSystemTypeDescription(file));//获取系统卷的类型

			
			System.out.println("file.getPath():" + file.getPath());
			System.out.println("空间未使用：" + (file.getFreeSpace() / 1024 / 1024) + "MB");
			System.out.println("已使用：" + (file.getUsableSpace() / 1024 / 1024) + "MB");
			System.out.println("总容量：" + (file.getTotalSpace() / 1024 / 1024) + "MB");
			System.out.println();

		}
	}
	
//	public static void getMemInfo() {
//		OperatingSystemMXBean  mem = ManagementFactory.getOperatingSystemMXBean();
//		System.out.println("总容量：" + (mem. / 1024 / 1024) + "MB");
//		
//		
//	}

	public static void main(String[] args) {
		getDiskInfo();
	}

}
