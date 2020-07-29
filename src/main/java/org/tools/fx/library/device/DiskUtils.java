package org.tools.fx.library.device;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

////  好像是window下管用
public class DiskUtils {
	public static String getSerialNumber(String drive) {
		String result = "";
		try {
			File file = File.createTempFile("damn", ".vbs");
			file.deleteOnExit();
			FileWriter fw = new java.io.FileWriter(file);
			String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
					+ "Set colDrives = objFSO.Drives\n" + "Set objDrive = colDrives.item(\"" + drive + "\")\n"
					+ "Wscript.Echo objDrive.SerialNumber"; // see note
			fw.write(vbs);
			fw.close();
			Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = input.readLine()) != null) {
				result += line;

			}
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.trim();
	}

	
	public static String getHdSerialInfo() {
		String line = "";
		String HdSerial = "";// 定义变量 硬盘序列号
		try {
			Process proces = Runtime.getRuntime().exec("cmd /c dir c:");// 获取命令行参数
			BufferedReader buffreader = new BufferedReader(new InputStreamReader(proces.getInputStream()));
			while ((line = buffreader.readLine()) != null) {
				if (line.indexOf("卷的序列号是 ") != -1) { // 读取参数并获取硬盘序列号
					HdSerial = line.substring(line.indexOf("卷的序列号是 ") + "卷的序列号是 ".length(), line.length());
					break;
					// System.out.println(HdSerial);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return HdSerial;// 返回硬盘序列号 卷的序列 非物理
	}
	
	
	public static void main(String[] args) {
		getSerialNumber("C");
	}
}
