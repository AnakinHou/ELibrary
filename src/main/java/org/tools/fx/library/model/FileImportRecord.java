package org.tools.fx.library.model;

import java.io.File;
import org.tools.fx.library.App;

public class FileImportRecord {
    private final String videoFormat =
            " .3gp, .mp4, .mkv, .rm, .rmvb, .wmv, .avi, .swf, .flv, .dat, .asf, .mpeg, .mpg, .ram, .mov, .divx, .dv, .vob, .qt, .cpk, .fli, .flc, .f4v, .m4v, .mod, .m2t, .webm, .mts, .ts, .m2ts";
    private final String audioFormat =
            " .wma, .wav, .mp3, .ape, .flac, .m4a, .aac, .ogg, .aiff, .f4v, .m4v, .mid, .midi, .rm";
    private final String docFormat =
            " .rtf, .txt, .pdf, .epub, .mobi, .doc, .docx, .ppt, .pptx, .xls, .xlsx";
    private final String imagesFormat =
            " .bmp, .jpg, .jpeg, .png, .gi, .tif, .tiff, .gif, .pcx, .tga, .exif, .fpx, .svg, .psd, .cdr, .pcd, .dxf, .ufo, .eps, .ai, .raw, .wmf, .webp";
    private final String compressedFormat =
            " .rar, .zip, .7z, .tar, .gz, .bz2, .jar, .iso, .cab, .arj, .lzh, .ace, .uue";
    private final String softwareFormat = " .exe, .dmp, .pkg, .app";

    private Long hdID;
    private String hdName;
    private String hdNickname;

    /**
     * 硬盘唯一号
     */
    private String hdUniqueCode;

    private Long ptID;
    /**
     * 分区序列号
     */
    private String ptUUID;

    /**
     * 挂载点 这个win和macOS不一样，分开保存
     */
    private String mountPoint = "";

    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件标题
     */
    private String subtitle;

    private boolean isFile;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件大小
     */
    private long fileSize;

    /**
     * 文件最后修改时间
     */
    private long lastModified;

    /**
     * 文件类型大分类<br>
     * 1 文档 DOCUMENT<br>
     * 2 视频 VIDEO<br>
     * 3 音乐 MUSIC<br>
     * 4 图片 PICTURE<br>
     * 5 软件 SOFTWARE<br>
     * 6 其他 OTHERS<br>
     */
    private String fileMainType;

    /**
     * 文件标记<br>
     * 1 学习 STUDY<br>
     * 2 工作 WORK<br>
     * 3 爱好 HOBBY<br>
     * 4 其他 OTHERS<br>
     */
    private String fileMark;

    /**
     * 文件标记 可以有多个，可以很长<br>
     * 比如 罗辑思维 相关 1 计算机 2 Java C python
     */
    private String fileTip;

    /**
     * 文件后缀名
     */
    private String fileSuffix;
    /**
     * 1 Windows<br>
     * 2 Linux<br>
     * 3 macOS
     */
    private String platform;

    public FileImportRecord(File file) {
        // this.mountPoint = file;
        this.fileName = file.getName();
        this.isFile = file.isFile();
        this.filePath = file.getAbsolutePath();
        if (file.isFile()) {
            this.fileSize = file.length();
            this.lastModified = file.lastModified();
            this.subtitle = this.fileName;
            int dot = this.fileName.lastIndexOf(".");
            if (dot >= 0) {
                this.fileSuffix = this.fileName.substring(dot).toLowerCase();
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
                if (videoFormat.indexOf(fileSuffix) > 0) {
                    this.fileMainType = "视频";
                } else if (audioFormat.indexOf(fileSuffix) > 0) {
                    this.fileMainType = "音乐";
                } else if (docFormat.indexOf(fileSuffix) > 0) {
                    this.fileMainType = "文档";
                } else if (imagesFormat.indexOf(fileSuffix) > 0) {
                    this.fileMainType = "图片";
                } else if (compressedFormat.indexOf(fileSuffix) > 0) {
                    this.fileMainType = "压缩文件";
                } else if (softwareFormat.indexOf(fileSuffix) > 0) {
                    this.fileMainType = "软件";
                }
            }
        }
    }


    public Long getHdID() {
        return hdID;
    }


    public void setHdID(Long hdID) {
        this.hdID = hdID;
    }


    public String getHdName() {
        return hdName;
    }

    public void setHdName(String hdName) {
        this.hdName = hdName;
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

    public Long getPtID() {
        return ptID;
    }

    public void setPtID(Long ptID) {
        this.ptID = ptID;
    }

    public String getPtUUID() {
        return ptUUID;
    }

    public void setPtUUID(String ptUUID) {
        this.ptUUID = ptUUID;
    }

    public String getMountPoint() {
        return mountPoint;
    }

    public void setMountPoint(String mountPoint) {
        this.mountPoint = mountPoint;
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


    public boolean isFile() {
        return isFile;
    }

    public void setFile(boolean isFile) {
        this.isFile = isFile;
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

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

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


}
