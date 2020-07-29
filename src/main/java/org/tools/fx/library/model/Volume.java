package org.tools.fx.library.model;

import java.util.ArrayList;
import java.util.List;
import org.tools.dev.hd.HWDiskStore;
import org.tools.dev.hd.HWPartition;
import org.tools.fx.library.App;
import org.tools.fx.library.entity.HardDrive;
import org.tools.fx.library.entity.Partition;
import org.tools.fx.library.enums.PlatformEnum;
import org.tools.fx.library.tools.Encode;

public class Volume {

    private Long hdID;
    private String name;
    private String nickname;

    private String model;
    private long size;
    // 1 是usb 0 不是 2 unkown
    // private int usbHD;
    // private String vendor;
    // private String vendorId;
    // private String productId;
    private String serialNumber;
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
        this.serialNumber = disk.getSerial().toUpperCase();
        // this. uniqueDeviceId = disk.;
        // 如果sesrialNumber 为空，那么就自动生成一个 实在没办法了
        if (serialNumber == null || serialNumber.isEmpty()) {
            // 硬盘size和下属的分区 size
            String sizes = size + "";
            for (HWPartition pt : disk.getPartitions()) {
                sizes += pt.getSize();
            }
            serialNumber = Encode.MD5(sizes);
        }
        volumes = new ArrayList<>();
    }


    public Volume(HWPartition partition) {
        this.vType = 2; // 分区
        // this.ptID;
        // this.identification = partition.getIdentification();
        this.name = partition.getName();
        this.nickname = partition.getName();
        this.size = partition.getSize();
        this.type = partition.getType();
        this.uuid = partition.getUuid().toUpperCase();
        this.mountPoint = partition.getMountPoint();
    }

    public Volume(HardDrive hd) {
        this.hdID = hd.getHdID();
        this.nickname = hd.getNickname();
        this.vType = 1; // 硬盘
        this.size = hd.getSize();
        if (App.os == PlatformEnum.WINDOWS) {
            this.name = hd.getWinName();
            this.model = hd.getWinModel();
            this.serialNumber = hd.getWinSerialNumber();
        } else {
            this.name = hd.getMacName();
            this.model = hd.getMacModel();
            this.serialNumber = hd.getMacSerialNumber();
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
//        System.out.println("============ Volume pt, hdID:"+hdID);
        this.vType = 2; // 分区
        this.nickname = pt.getNickname();
        // this.identification = pt.getIdentification();

        this.size = pt.getSize();
        // this.type = pt.getType();
        if (App.os == PlatformEnum.WINDOWS) {
            this.uuid = pt.getWinUUID().toUpperCase();
            this.name = pt.getWinName();
            this.mountPoint = pt.getWinMountPoint();
        } else {
            this.uuid = pt.getMacUUID().toUpperCase();
            this.name = pt.getMacName();
            this.mountPoint = pt.getMacMountPoint();
        }
    }

    public String toString() {
        // 如果是硬盘
        if (this.vType == 1) {
            if (this.nickname == null || this.nickname.isEmpty()) {
                return this.name + "[hdID:" + hdID + "]";
            } else {
                return this.name + " [" + this.nickname + "]" + "[hdID:" + hdID + "]";
            }
        } else {
            return this.name + " [" + this.mountPoint + "]" + "[ptID:" + ptID + "]";
        }
    }

    public String print() {
        StringBuilder sb = new StringBuilder(this.getName());
        sb.append(":  ");
        sb.append(" model: ").append(getModel());
        sb.append(",  SerialNumber: ").append(getSerialNumber());
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


    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
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
