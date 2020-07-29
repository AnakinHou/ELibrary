package org.tools.fx.library;

import java.util.ArrayList;
import java.util.List;
import org.tools.dev.hd.HWDiskStore;
import org.tools.dev.hd.HWPartition;
import org.tools.fx.library.device.DiskHelper;
import org.tools.fx.library.enums.PlatformEnum;
import org.tools.fx.library.model.Volume;
import org.tools.fx.library.tools.Encode;

public class App {

    public static PlatformEnum os;

    public static List<Volume> currentVolumes;

    public static List<HWDiskStore> currentDisks;

    static {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.startsWith("win")) {
            os = PlatformEnum.WINDOWS;
        } else if (osName.startsWith("mac")) {
            os = PlatformEnum.MACOS;
        } else {
            os = PlatformEnum.UNKNOWN;
        }
        // currentVolumes = new ArrayList<>();
    }

    public static void refreshDisks() {
        currentDisks = DiskHelper.getInstance().getDisks();
        currentVolumes = new ArrayList<>();
        // 先循环硬盘
        for (int i = 0; i < App.currentDisks.size(); i++) {
            HWDiskStore disk = App.currentDisks.get(i);
            if (os == PlatformEnum.WINDOWS) {
                // TODO
                Volume volume = new Volume(disk);
                volume.setActive(true);
                currentVolumes.add(volume);
                // 循环分区
                List<HWPartition> partitions = disk.getPartitions();
                for (HWPartition part : partitions) {
                    if (part.getMountPoint() == null || part.getMountPoint().isEmpty()) {
                        continue;
                    }
                    Volume volume2 = new Volume(part);
                    volume2.setActive(true);
                    volume2.setSerialNumber(disk.getSerial());
                    volume.getVolumes().add(volume2);
                }
            } else {
                if (disk.getModel().equals("Disk Image")) {
                    continue;
                }
                Volume volume = new Volume(disk);
                volume.setActive(true);
                currentVolumes.add(volume);
                // 循环分区
                List<HWPartition> partitions = disk.getPartitions();
                for (HWPartition part : partitions) {
                    if (part.getMountPoint() == null || part.getMountPoint().isEmpty()) {
                        continue;
                    }

                    if (part.getMountPoint().equals("/")
                            || part.getMountPoint().startsWith("/Volumes")) {
                        Volume volume2 = new Volume(part);
                        volume2.setActive(true);
                        volume2.setSerialNumber(disk.getSerial());
                        // 如果是移动硬盘，MacOS下无法获得分区 UUID
                        // 所以只能自己生成一个md5的了， 硬盘model+硬盘序列号+分区size 生成一个md5值
                        if (volume2.getUuid() == null || volume2.getUuid().equals("UNKNOWN")||  volume2.getUuid().isEmpty()) {
                            String UUID = Encode.MD5(volume2.getModel() + volume2.getSerialNumber()
                                    + volume2.getSize());
                            volume2.setUuid(UUID);
                        }
                        volume.getVolumes().add(volume2);
                    }
                }
            }

        }
    }

}
