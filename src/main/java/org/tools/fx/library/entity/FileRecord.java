package org.tools.fx.library.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "FILERECORD")
public class FileRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILE_ID")
    private Long fileId;


    @Column(name = "HD_ID")
    private Long hdID;

    @Column(name = "PT_ID")
    private Long ptID;


    @Column(name = "HD_NICKNAME")
    private String hdNickname;
    /**
     * 
     */
    @Column(name = "HD_UNIQUE_CODE")
    private String hdUniqueCode;

    @Column(name = "UUID")
    private String uuid;

    @Column(name = "FILENAME")
    private String fileName;

    /**
     * 这个字段默认跟 fileName一样<br>
     * 去除重复文件时，可以作为唯一键
     */
    @Column(name = "SUBTITLE")
    private String subtitle;

    @Transient
    private String mountPoint;

    /**
     * 这里的文件路径 <br>
     * windows下不包含盘符<br>
     * 因为移动硬盘每次插入电脑 盘符都不一样<br>
     */
    @Column(name = "FILEPATH")
    private String filePath;


    @Column(name = "FILESIZE")
    private long fileSize;
    /*
     * 文件MD5值<br> 用来去重复
     */
    // @Column(name = "FILEMD5")
    // private Long fileMD5;

    /**
     * 文件类型大分类<br>
     * 1 文档 DOCUMENT<br>
     * 2 视频 VIDEO<br>
     * 3 音乐 MUSIC<br>
     * 4 图片 PICTURE<br>
     * 5 软件 SOFTWARE<br>
     * 6 压缩文件 COMPRESSED<br>
     * 7 其他 OTHERS<br>
     */
    @Column(name = "FILEMAINTYPE")
    private String fileMainType;

    /**
     * 文件标记<br>
     * 1 学习 STUDY<br>
     * 2 开发 DEVELOP<br>
     * 3 工作 WORK<br>
     * 4 爱好 HOBBY<br>
     * 5 其他 OTHERS<br>
     */
    @Column(name = "FILEMARK")
    private String fileMark;

    /**
     * 文件标记 可以有多个，可以很长<br>
     * 比如 罗辑思维 相关 1 计算机 2 Java C python
     */
    @Column(name = "FILETIP")
    private String fileTip;

    /**
     * 后缀名
     */
    @Column(name = "FILESUFFIX")
    private String fileSuffix;

    /**
     * 1 Windows<br>
     * 2 Linux<br>
     * 3 macOS
     */
    @Column(name = "PLATFORM")
    private String platform;

    @Column(name = "LASTMODIFIED")
    private long lastModified;


    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Long getHdID() {
        return hdID;
    }

    public void setHdID(Long hdID) {
        this.hdID = hdID;
    }

    public Long getPtID() {
        return ptID;
    }

    public void setPtID(Long ptID) {
        this.ptID = ptID;
    }

    public String getHdNickname() {
        return hdNickname;
    }

    public void setHdNickname(String hdNickname) {
        this.hdNickname = hdNickname;
    }


    public String getHdUniqueCode() {
        return hdUniqueCode;
    }

    public void setHdUniqueCode(String hdUniqueCode) {
        this.hdUniqueCode = hdUniqueCode;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getMountPoint() {
        return mountPoint;
    }

    public void setMountPoint(String mountPoint) {
        this.mountPoint = mountPoint;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    // public Long getFileMD5() {
    // return fileMD5;
    // }
    //
    // public void setFileMD5(Long fileMD5) {
    // this.fileMD5 = fileMD5;
    // }

    public String getFileMainType() {
        return fileMainType;
    }

    public void setFileMainType(String fileMainType) {
        this.fileMainType = fileMainType;
    }

    public String getFileMark() {
        return fileMark;
    }

    public void setFileMark(String fileMark) {
        this.fileMark = fileMark;
    }

    public String getFileTip() {
        return fileTip;
    }

    public void setFileTip(String fileTip) {
        this.fileTip = fileTip;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

}
