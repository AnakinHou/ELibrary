package org.tools.fx.library.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * 所有的硬盘
 * 
 * @author Medusa
 *
 */
@Entity
@Table(name = "HARDDRIVE")
public class HardDrive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HD_ID")
    private Long hdID;

    // /**
    // * 1 windows系统下的硬盘<br>
    // * 2 macOS下的硬盘<br>
    // * 3 Linux下的硬盘<br>
    // */
    // @Column(name = "PLATFORM")
    // private int platform;

    @Column(name = "WIN_NAME")
    private String winName;

    @Column(name = "MAC_NAME")
    private String macName;

    @Column(name = "NICKNAME")
    private String nickname;


    @Column(name = "WIN_MODEL")
    private String winModel;

    @Column(name = "MAC_MODEL")
    private String macModel;


    @Column(name = "SIZE")
    private long size;

    @Column(name = "HD_UNIQUE_CODE")
    private String hdUniqueCode;

    /**
     * 0 不是<br>
     * 1 是usb <br>
     * 2 unkown
     */
    // @Column(name = "USBHD")
    // private int usbHD;

    // @Column(name = "VENDOR")
    // private String vendor;
    //
    // @Column(name = "VENDORID")
    // private String vendorId;
    //
    // @Column(name = "PRODUCTID")
    // private String productId;

    // @Column(name = "WIN_SERIALNUMBER")
    // private String winSerialNumber;
    //
    // @Column(name = "MAC_SERIALNUMBER")
    // private String macSerialNumber;

    // @Column(name = "UNIQUEDEVICEID")
    // private String uniqueDeviceId;

    @OneToMany(mappedBy = "hardDrive", fetch = FetchType.EAGER)
    @Cascade(value = CascadeType.PERSIST)
    private List<Partition> partitionList;

    public Long getHdID() {
        return hdID;
    }

    public void setHdID(Long hdID) {
        this.hdID = hdID;
    }

    // public int getPlatform() {
    // return platform;
    // }
    //
    // public void setPlatform(int platform) {
    // this.platform = platform;
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

    public String getWinModel() {
        return winModel;
    }

    public void setWinModel(String winModel) {
        this.winModel = winModel;
    }

    public String getMacModel() {
        return macModel;
    }

    public void setMacModel(String macModel) {
        this.macModel = macModel;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }


    // public int getUsbHD() {
    // return usbHD;
    // }
    //
    // public void setUsbHD(int usbHD) {
    // this.usbHD = usbHD;
    // }

    // public String getVendor() {
    // return vendor;
    // }
    //
    // public void setVendor(String vendor) {
    // this.vendor = vendor;
    // }
    //
    // public String getVendorId() {
    // return vendorId;
    // }
    //
    // public void setVendorId(String vendorId) {
    // this.vendorId = vendorId;
    // }
    //
    // public String getProductId() {
    // return productId;
    // }
    //
    // public void setProductId(String productId) {
    // this.productId = productId;
    // }

    // public String getWinSerialNumber() {
    // return winSerialNumber;
    // }
    //
    // public void setWinSerialNumber(String winSerialNumber) {
    // this.winSerialNumber = winSerialNumber;
    // }
    //
    // public String getMacSerialNumber() {
    // return macSerialNumber;
    // }
    //
    // public void setMacSerialNumber(String macSerialNumber) {
    // this.macSerialNumber = macSerialNumber;
    // }

    // public String getUniqueDeviceId() {
    // return uniqueDeviceId;
    // }
    //
    // public void setUniqueDeviceId(String uniqueDeviceId) {
    // this.uniqueDeviceId = uniqueDeviceId;
    // }

    public String getHdUniqueCode() {
        return hdUniqueCode;
    }

    public void setHdUniqueCode(String hdUniqueCode) {
        this.hdUniqueCode = hdUniqueCode;
    }

    public List<Partition> getPartitionList() {
        return partitionList;
    }

    public void setPartitionList(List<Partition> partitionList) {
        this.partitionList = partitionList;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(this.getWinName());
        sb.append(": ").append(getMacName());
        sb.append(",  winModel: ").append(getWinModel());
        sb.append(",  macModel: ").append(getMacModel());
        sb.append(",  hdUniqueCode: ").append(getHdUniqueCode());
        // sb.append(", win_SerialNumber: ").append(getWinSerialNumber());
        // sb.append(", mac_SerialNumber: ").append(getMacSerialNumber());
        // sb.append(", size: ").append(FormatUtil.formatBytesDecimal(getSize()));
        sb.append("size: ").append(String.format("%d %s", size / 1024 / 1024, "MB"));
        // sb.append(", usbHD: ").append(getUsbHD());
        // sb.append(", vendor: ").append(getVendor());
        // sb.append(", vendorId: ").append(getVendorId());
        // sb.append(", productId: ").append(getProductId());
        // sb.append(", UniqueDeviceId: ").append(getUniqueDeviceId());

        List<Partition> partitions = getPartitionList();
        for (Partition part : partitions) {
            sb.append("\n |--- ").append(part.getWinName()).append(part.getMacName());
            // sb.append(", (id:").append(part.getIdentification()).append(") ");
            // sb.append(", (type:").append(part.getType()).append(") ");
            sb.append(", (  uuid:").append(part.getUuid()).append(") ");
            // sb.append(", (Maj:Min=").append(part.getMajor()).append(":").append(part.getMinor())
            // .append(")");
            // sb.append(", (size: ").append(FormatUtil.formatBytesDecimal(part.getSize()));
            sb.append("size: ")
                    .append(String.format("%d %s", part.getSize() / 1024 / 1024.0, "MB"));
            sb.append(" @ win:" + part.getWinMountPoint());
            sb.append(" @ mac:" + part.getMacMountPoint());
        }
        return sb.toString();

    }

}
