package org.tools.fx.library;

import java.util.ArrayList;
import java.util.Arrays;
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
        currentVolumes = new ArrayList<Volume>();
        // 先循环硬盘
        for (int i = 0; i < App.currentDisks.size(); i++) {
            HWDiskStore disk = App.currentDisks.get(i);
            long[] sizeArr = null;
            Volume volume = null;
            if (os == PlatformEnum.WINDOWS) {
                // TODO
                volume = new Volume(disk);
                volume.setActive(true);
                currentVolumes.add(volume);
                // 循环分区
                List<HWPartition> partitions = disk.getPartitions();
                sizeArr = new long[partitions.size()];
//                for (HWPartition part : partitions) {
                for (int z = 0; z < disk.getPartitions().size(); z++) {
                    HWPartition part = disk.getPartitions().get(z);
                    if (part.getMountPoint() == null || part.getMountPoint().isEmpty()) {
                        sizeArr[z] = 0;
                        continue;
                    }
                    sizeArr[z] = part.getSize();
                    Volume volume2 = new Volume(part,disk.getSize());
                    volume2.setActive(true);
//                    volume2.setHdUniqueCode(volume.getHdUniqueCode());
                    volume.getVolumes().add(volume2);
                }
            } else {
                if (disk.getModel().equals("Disk Image")) {
                    continue;
                }
                volume = new Volume(disk);
                volume.setActive(true);
                currentVolumes.add(volume);
                // 循环分区
                List<HWPartition> partitions = disk.getPartitions();
                sizeArr = new long[partitions.size()];

                for (int z = 0; z < disk.getPartitions().size(); z++) {
                    HWPartition part = disk.getPartitions().get(z);

                    if (part.getMountPoint() == null || part.getMountPoint().isEmpty()) {
                        sizeArr[z] = 0;
                        continue;
                    }

                    if (part.getMountPoint().equals("/")
                            || part.getMountPoint().startsWith("/Volumes")) {
                        sizeArr[z] = part.getSize();

                        Volume volume2 = new Volume(part,disk.getSize());
                        volume2.setActive(true);
                        // volume2.setHdUniqueCode(volume.getHdUniqueCode());
                        volume.getVolumes().add(volume2);
                    } else {
                        sizeArr[z] = 0;
                    }
                }
            }
            // 根据  硬盘size和下属的分区 size  生成一个 硬盘识别码
            Arrays.sort(sizeArr);
            String sizes = "";
            long hdSize = 0;
//            System.out.println("=====   hd size:" + sizes);
            for (int k = 0; k < sizeArr.length; k++) {
                if (sizeArr[k] == 0) {
                    continue;
                }
                sizes += sizeArr[k];
                hdSize +=sizeArr[k];
//                System.out.println("===========   pt size:" + sizeArr[k]);
            }
//            System.out.println("======= === MD5前：" + hdSize+sizes);
            String hdUniqueCode = Encode.MD5(hdSize+sizes);
//            System.out.println("===== "+Encode.MD5(sizes));
            volume.setHdUniqueCode(hdUniqueCode);
            volume.setSize(hdSize);
//            System.out.println("================== MD5后：" + hdUniqueCode);
            for (Volume ptVlm : volume.getVolumes()) {
                ptVlm.setHdUniqueCode(hdUniqueCode);
            }
        }

        ///////// 打印一下 currentVolumes
        System.out.println("==============currentVolumes  刷新后================");
        for (int i = 0; i < currentVolumes.size(); i++) {
            System.out.println(currentVolumes.get(i).print());
        }


    }

}
