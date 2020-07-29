package org.tools.fx.library.service;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.tools.fx.library.App;
import org.tools.fx.library.db.DBHelper;
import org.tools.fx.library.entity.FileRecord;
import org.tools.fx.library.entity.HardDrive;
import org.tools.fx.library.entity.User;
import org.tools.fx.library.enums.PlatformEnum;
import org.tools.fx.library.model.FileImportRecord;
import org.tools.fx.library.model.Result;

public class FileImportService {

    private final static ThreadLocal<FileImportService> service =
            new ThreadLocal<FileImportService>() {
                protected FileImportService initialValue() {
                    return new FileImportService();
                }
            };

    private FileImportService() {}

    public static FileImportService getService() {
        return service.get();
    }

    private FileRecord generateFileRecordFromFileImport(FileImportRecord fileImport) {
        FileRecord record = new FileRecord();
        // record.setFileId( );
        record.setFileMainType(fileImport.getFileMainType());
        record.setFileMark(fileImport.getFileMark());
        // record.setFileMD5( );
        record.setFileName(fileImport.getFileName());
        record.setSubtitle(fileImport.getSubtitle());
        record.setFilePath(fileImport.getFilePath());
        record.setFileSize(fileImport.getFileSize());
        record.setFileSuffix(fileImport.getFileSuffix());
        record.setFileTip(fileImport.getFileTip());
        record.setHdID(fileImport.getHdID());
        record.setPtID(fileImport.getPtID());
        record.setHdUniqueCode(fileImport.getHdUniqueCode());
        if (App.os == PlatformEnum.WINDOWS) {
            record.setWinUUID(fileImport.getPtUUID());
        } else {
            record.setMacUUID(fileImport.getPtUUID());
        }
        // record.setHdName(fileImport.getHdName());
        record.setHdNickname(fileImport.getHdNickname());
        record.setLastModified(fileImport.getLastModified());
        // record.setMountPoint( fileImport.get);
        record.setPlatform(fileImport.getPlatform());

        return record;
    }

    /**
     * 
     * @param fileList
     * @return
     */
    public Result importFile(List<FileImportRecord> fileList) {
        if (fileList == null || fileList.size() == 0) {
            return new Result(false, "没有要保存的文件");
        }
        EntityManager em = DBHelper.getEM();

        // HardDrive hd = em.find(HardDrive.class, fileList.get(0));
        // if (hd == null) {
        // return new Result(false, "文件所在的硬盘，在数据库中不存在");
        // }
        // if

        EntityTransaction tran = em.getTransaction();
        tran.begin();
        int count = 0;

        try {
            for (int i = 0; i < fileList.size(); i++) {
                FileImportRecord fir = fileList.get(i);
                FileRecord record = generateFileRecordFromFileImport(fir);
                em.persist(record);
                count++;
                if (count == 20) {
                    // tran.commit();
                    em.flush();
                    em.clear();

                    // tran = em.getTransaction();
                    // tran.begin();
                    count = 1;
                }
            }

            tran.commit();
            em.close();
            return new Result(true, "文件导入成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "文件导入失败");
        }
    }

}
