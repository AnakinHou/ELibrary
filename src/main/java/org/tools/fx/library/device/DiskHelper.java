package org.tools.fx.library.device;

import java.util.List;
import org.tools.dev.common.MacHWDiskStore;
import org.tools.dev.common.MacUsbDevice;
import org.tools.dev.common.WindowsHWDiskStore;
import org.tools.dev.hd.HWDiskStore;
import org.tools.dev.hd.HWPartition;
import org.tools.dev.usb.UsbDevice;
import org.tools.fx.library.App;
import org.tools.fx.library.enums.PlatformEnum;

/**
 * 硬盘工具类
 * 
 * @author hydra
 *
 */
public class DiskHelper {

    // public String os = null;

    private static class SingletonHoler {
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static DiskHelper instance = new DiskHelper();
    }

    private DiskHelper() {
        // os = System.getProperty("os.name");
    }

    public static DiskHelper getInstance() {
        return SingletonHoler.instance;
    }

    public List<HWDiskStore> getDisks() {
        // System.out.println(App.os);
        if (App.os == PlatformEnum.WINDOWS) {
            return getWinDisks();
        } else if (App.os == PlatformEnum.MACOS) {
            return getMacDisks();
        } else {
            System.out.println("Sorry, This program just work on Windows or MacOS");
            return null;
        }
    }



    private List<HWDiskStore> getMacDisks() {
        // 获取所有的硬盘信息
        List<HWDiskStore> macDiskList = MacHWDiskStore.getDisks();
        // 获取USB设备信息
        // List<UsbDevice> usbList = MacUsbDevice.getUsbDevices(true);
//        for (int i = 0; i < macDiskList.size(); i++) {
//            HWDiskStore disk = macDiskList.get(i);
//            if (disk.getModel().equals("Disk Image")) {
//                continue;
//            }
            // if (disk.getSerial() == null || "".equals(disk.getSerial())) {
            // UsbDevice usbDisk = getDiskFromUSB(usbList, disk.getModel());
            // if (usbDisk != null) {
            // usbHD = 1;
            // vendor = usbDisk.getVendor();
            // vendorId = usbDisk.getVendorId();
            // productId = usbDisk.getProductId();
            // serialNumber = usbDisk.getSerialNumber();
            // uniqueDeviceId = usbDisk.getUniqueDeviceId();
            // disk.setSerial(usbDisk.getSerialNumber());
            // }
            // }
//            System.out.println("disk: " + disk.toString());
//            List<HWPartition> partitions = disk.getPartitions();
//            for (HWPartition p : partitions) {
//                System.out.println("    |-- " + p.toString());
//            }
//        }


        return macDiskList;
    }

    private List<HWDiskStore> getWinDisks() {
        List<HWDiskStore> winDiskList = WindowsHWDiskStore.getDisks();
//        for (int i = 0; i < winDiskList.size(); i++) {
//            HWDiskStore disk = winDiskList.get(i);
//            System.out.println("disk: " + disk.toString());
//            List<HWPartition> partitions = disk.getPartitions();
//            for (HWPartition p : partitions) {
//                System.out.println("    |-- " + p.toString());
//            }
//        }
        return winDiskList;
    }


    public static void main(String[] args) {
        DiskHelper.getInstance().getDisks();
    }


}
