package org.tools.fx.library.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.tools.dev.hd.HWDiskStore;
import org.tools.dev.hd.HWPartition;
import org.tools.fx.library.App;
import org.tools.fx.library.entity.HardDrive;
import org.tools.fx.library.entity.Partition;
import org.tools.fx.library.enums.PlatformEnum;
import org.tools.fx.library.tools.Encode;

public class Volume {

    /**
     * 这是个约数  只要大于这个值那么
     */
    private final long  approximateTB = 1000 * 1000 * 1000 * 1000;
    private Long hdID;
    private String name;
    private String nickname;

    private String model;
    private long size;
    private String hdUniqueCode;
    // 1 是usb 0 不是 2 unkown
    // private int usbHD;
    // private String vendor;
    // private String vendorId;
    // private String productId;
    // private String serialNumber;
    // private String uniqueDeviceId;
    /**
     * 1 硬盘<br>
     * 2 分区
     */
    private int vType;
    private Long ptID;
    // private String identification;
    private String type; // 比如 NTFS
    private String uuid;
    private String mountPoint;

    /**
     * 当前硬盘或者分区是否是活动状态<br>
     * 也就是说 是不是已经连接电脑
     */
    private boolean active;

    /**
     * 分区
     */
    private List<Volume> volumes;

    public Volume(String name, String model) {
        this.name = name;
        this.model = model;
    }

    public Volume(HWDiskStore disk) {
        // this.hdID = disk.get;
        this.name = disk.getName();
        this.nickname = disk.getName();
        this.vType = 1; // 硬盘
        this.model = disk.getModel();
        this.size = disk.getSize();
        // 1 是usb 0 不是 2 unkown
        // if(App.os.equals("win")) {
        // }
        // this. usbHD ;
        // this. vendor = disk.getve;
        // this. vendorId =disk.get;
        // this.productId;
        // this.serialNumber = disk.getSerial().toUpperCase();
        // this. uniqueDeviceId = disk.;
        // 如果sesrialNumber 为空，那么就自动生成一个 实在没办法了
        // if (serialNumber == null || serialNumber.isEmpty()) {
        // 硬盘size和下属的分区 size

        volumes = new ArrayList<>();
    }


    public Volume(HWPartition partition ) {
        this.vType = 2; // 分区
        // this.ptID;
        // this.identification = partition.getIdentification();
        this.name = partition.getName();
        this.nickname = partition.getName();
        this.size = partition.getSize();
        this.type = partition.getType();
        this.mountPoint = partition.getMountPoint();
//        System.out.println("============= partition.getMountPoint():"+partition.getMountPoint());
        // 如果是移动硬盘，MacOS下无法获得分区 UUID
        // 所以只能自己生成一个md5的了，硬盘size+ 分区size 生成一个md5值
        // 硬盘model这个不准， windows下和macOS下 不一样
        // mountPoint也不准，因为有可能 修改分区名称
//        this.uuid = Encode.MD5(hdSize + "" + this.size);
//        if (partition.getUuid() == null || partition.getUuid().equalsIgnoreCase("unknown")
//                || partition.getUuid().isEmpty()) {
//            System.out.println("==================== HWPartition  uuid:" + partition.getUuid()
//                    + ",  fakeUUID:" + uuid);
//        } else {
//            this.uuid = partition.getUuid().toUpperCase();
//        }
    }

    public Volume(HardDrive hd) {
        this.hdID = hd.getHdID();
        this.nickname = hd.getNickname();
        this.vType = 1; // 硬盘
        this.size = hd.getSize();
        this.hdUniqueCode = hd.getHdUniqueCode();
//        System.out.println("========== Volume   HardDrive  size:" + size);
        if (App.os == PlatformEnum.WINDOWS) {
            this.name = hd.getWinName() == null ? "" :hd.getWinName();
            this.model = hd.getWinModel();
            // this.serialNumber = hd.getWinSerialNumber();
        } else {
            this.name = hd.getMacName()==null ? "" :hd.getMacName();
            this.model = hd.getMacModel();
            // this.serialNumber = hd.getMacSerialNumber();
        }
        if (this.nickname == null || this.nickname.isEmpty()) {
            this.nickname = this.model;
        }
        // this. uniqueDeviceId = disk.;
        volumes = new ArrayList<>();
    }

    public Volume(Partition pt) {
        this.ptID = pt.getPtID();
        this.hdID = pt.getHardDrive().getHdID();
        // System.out.println("============ Volume pt, hdID:"+hdID);
        this.vType = 2; // 分区
        this.nickname = pt.getNickname();
        // this.identification = pt.getIdentification();

        this.size = pt.getSize();
//        System.out.println("=================== Volume  Partition size:" + size);
        this.uuid = pt.getUuid()  ;
//        System.out.println("=================== Volume  Partition uuid:" + uuid);
        // this.type = pt.getType();
        if (App.os == PlatformEnum.WINDOWS) {
            this.name = pt.getWinName();
//            this.mountPoint = pt.getWinMountPoint();
        } else {
            this.name = pt.getMacName();
//            this.mountPoint = pt.getMacMountPoint();
        }
    }


    public String toString() {
        // 如果是硬盘
        if (this.vType == 1) {
            if (this.nickname == null || this.nickname.isEmpty()) {
//                return this.name + "[hdID:" + hdID + "]";
                return this.name  ;
            } else {
//                return this.name + " [" + this.nickname + "]" + "[hdID:" + hdID + "]";
                return this.name + " [" + this.nickname + "]"  ;
            }
        } else {
//            return this.name + " [" + this.mountPoint + "]" + "[ptID:" + ptID + "]";
            if (this.nickname == null || this.nickname.isEmpty()) {
                return this.name + " [" + this.mountPoint + "]";
            } else {
                return this.nickname + " [" + this.mountPoint + "]";
            }
        }
    }

    public String print() {
        StringBuilder sb = new StringBuilder(this.getName());
        sb.append(":  ");
        sb.append(" name: ").append(getName());
        sb.append(" nickname: ").append(getNickname());
        sb.append(" model: ").append(getModel());
        // sb.append(", SerialNumber: ").append(getSerialNumber());
        sb.append(",  hdUniqueCode: ").append(getHdUniqueCode());
        // sb.append(", size: ").append(FormatUtil.formatBytesDecimal(getSize()));
        sb.append("size: ").append(String.format("%d %s", size / 1024 / 1024, "MB"));

        List<Volume> partitions = getVolumes();
        if (partitions != null) {

            for (Volume part : partitions) {
                sb.append("\n |--- ").append(part.getName());
                sb.append(", nickname ").append(part.getNickname());
                sb.append(", (type:").append(part.getType()).append(") ");
                sb.append(", (uuid:").append(part.getUuid()).append(") ");
                sb.append("size: ")
                        .append(String.format("%d %s", part.getSize() / 1024 / 1024, "MB"));
                sb.append(", (mountPoint: ").append(part.getMountPoint());
            }
        }
        return sb.toString();

    }

    public Long getHdID() {
        return hdID;
    }

    public void setHdID(Long hdID) {
        this.hdID = hdID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getHdUniqueCode() {
        return hdUniqueCode;
    }

    public void setHdUniqueCode(String hdUniqueCode) {
        this.hdUniqueCode = hdUniqueCode;
    }

    public int getvType() {
        return vType;
    }

    public void setvType(int vType) {
        this.vType = vType;
    }

    public Long getPtID() {
        return ptID;
    }

    public void setPtID(Long ptID) {
        this.ptID = ptID;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMountPoint() {
        return mountPoint;
    }

    public void setMountPoint(String mountPoint) {
        this.mountPoint = mountPoint;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Volume> getVolumes() {
        return volumes;
    }



}
