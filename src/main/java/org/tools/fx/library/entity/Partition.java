package org.tools.fx.library.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 硬盘下的分区
 * 
 * @author Medusa
 *
 */
@Entity
@Table(name = "PARTITION")
public class Partition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PT_ID")
    private Long ptID;

    /**
     * 1 windows系统下的硬盘<br>
     * 2 macOS下的硬盘<br>
     * 3 Linux下的硬盘<br>
     * 这个字段其实不需要，直接取所属硬盘的 platform就行了， 这个是冗余字段
     */
    // @Column(name = "PLATFORM")
    // private int platform;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = HardDrive.class,
            cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "HARDDRIVE_ID") // 外键为HD_ID
    // @Column(name = "HDID")
    private HardDrive hardDrive;

    // @Column(name = "IDENTIFICATION")
    // private String identification;

    @Column(name = "WIN_NAME")
    private String winName;

    @Column(name = "MAC_NAME")
    private String macName;

    @Column(name = "NICKNAME")
    private String nickname;


    @Column(name = "WIN_UUID")
    private String winUUID;

    @Column(name = "MAC_UUID")
    private String macUUID;

    @Column(name = "SIZE")
    private long size;

    // @Column(name = "MAJOR")
    // private int major;
    //
    // @Column(name = "MINOR")
    // private int minor;

    @Column(name = "WIN_MOUNTPOINT")
    private String winMountPoint;

    @Column(name = "MAC_MOUNTPOINT")
    private String macMountPoint;

    // public Partition(String identification, String name, String type, String uuid, long size,
    // int major, int minor, String mountPoint) {
    // this.identification = identification;
    // this.name = name;
    // this.type = type;
    // this.uuid = uuid;
    // this.size = size;
    // this.major = major;
    // this.minor = minor;
    // this.mountPoint = mountPoint;
    // }


    public Long getPtID() {
        return ptID;
    }

    public void setPtID(Long ptID) {
        this.ptID = ptID;
    }

    // public int getPlatform() {
    // return platform;
    // }
    //
    // public void setPlatform(int platform) {
    // this.platform = platform;
    // }

    public HardDrive getHardDrive() {
        return hardDrive;
    }

    public void setHardDrive(HardDrive hardDrive) {
        this.hardDrive = hardDrive;
    }

    // public String getIdentification() {
    // return identification;
    // }
    //
    // public void setIdentification(String identification) {
    // this.identification = identification;
    // }

    public String getNickname() {
        return nickname;
    }

    public String getWinName() {
        return winName;
    }

    public void setWinName(String winName) {
        this.winName = winName;
    }

    public String getMacName() {
        return macName;
    }

    public void setMacName(String macName) {
        this.macName = macName;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getWinUUID() {
        return winUUID;
    }

    public void setWinUUID(String winUUID) {
        this.winUUID = winUUID;
    }

    public String getMacUUID() {
        return macUUID;
    }

    public void setMacUUID(String macUUID) {
        this.macUUID = macUUID;
    }

    public String getWinMountPoint() {
        return winMountPoint;
    }

    public void setWinMountPoint(String winMountPoint) {
        this.winMountPoint = winMountPoint;
    }

    public String getMacMountPoint() {
        return macMountPoint;
    }

    public void setMacMountPoint(String macMountPoint) {
        this.macMountPoint = macMountPoint;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // sb.append(getIdentification()).append(": ");
        sb.append(getWinName()).append(":").append(getMacName());
        // sb.append("(").append(getType()).append(") ");
        // sb.append("Maj:Min=").append(getMajor()).append(":").append(getMinor()).append(", ");
        // sb.append("size: ").append(FormatUtil.formatBytesDecimal(getSize()));
        sb.append("size: ").append(String.format("%d %s", size / 1024 / 1024, "MB"));
        sb.append(" @ win:" + getWinMountPoint());
        sb.append(" @ mac:" + getMacMountPoint());
        //
        // sb.append("\n |--- ").append(part.getName());
        // sb.append(", (id:").append(part.getIdentification()).append(") ");
        // sb.append(", (type:").append(part.getType()).append(") ");
        // sb.append(", (uuid:").append(part.getUuid()).append(") ");
        // sb.append(", (Maj:Min=").append(part.getMajor()).append(":").append(part.getMinor())
        // .append(")");
        // // sb.append(", (size: ").append(FormatUtil.formatBytesDecimal(part.getSize()));
        // sb.append("size: ").append(String.format("%d %s", part.getSize() / 1024 / 1024, "MB"));
        // sb.append( " @ win:" + part.getWinMountPoint());
        // sb.append( " @ mac:" + part.getMacMountPoint());
        //
        return sb.toString();
    }

}
