package org.tools.fx.library.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.tools.fx.library.App;
import org.tools.fx.library.db.DBHelper;
import org.tools.fx.library.entity.HardDrive;
import org.tools.fx.library.entity.Partition;
import org.tools.fx.library.enums.PlatformEnum;
import org.tools.fx.library.model.Result;
import org.tools.fx.library.model.Volume;

public class HardDriveService {

    private final static ThreadLocal<HardDriveService> service =
            new ThreadLocal<HardDriveService>() {
                protected HardDriveService initialValue() {
                    return new HardDriveService();
                }
            };

    private HardDriveService() {}

    public static HardDriveService getService() {
        return service.get();
    }


    private HardDrive generateHardDrive(Volume volume) {
        HardDrive hd = new HardDrive();

        hd.setNickname(volume.getNickname());
        // hd.setProductId(productId);
        hd.setSize(volume.getSize());
        hd.setHdUniqueCode(volume.getHdUniqueCode());
        if (App.os == PlatformEnum.WINDOWS) {
            hd.setWinName(volume.getName());
            hd.setWinModel(volume.getModel());
//            hd.setWinSerialNumber(volume.getSerialNumber());
        } else {
            hd.setMacName(volume.getName());
            hd.setMacModel(volume.getModel());
//            hd.setMacSerialNumber(volume.getSerialNumber());
        }
        return hd;
    }

    private Partition generatePartiton(Volume volume) {
        Partition pt = new Partition();
        // pt.setHardDriveID(hardDriveID);
        pt.setNickname(volume.getNickname());
        pt.setSize(volume.getSize());
        if (App.os == PlatformEnum.WINDOWS) {
            pt.setWinUUID(volume.getUuid());
            pt.setWinName(volume.getName());
            pt.setWinMountPoint(volume.getMountPoint());
        } else {
            pt.setMacUUID(volume.getUuid());
            pt.setMacName(volume.getName());
            pt.setMacMountPoint(volume.getMountPoint());
        }
        return pt;
    }

    /**
     * 硬盘或者分区 <br>
     * 有ID更新 <br>
     * 无ID 插入
     * 
     * @return
     */
    public Result updateHardDrive(List<Volume> volumeList) {
        EntityManager em = DBHelper.getEM();
        EntityTransaction tran = em.getTransaction();
        tran.begin();
        try {
            for (int i = 0; i < volumeList.size(); i++) {
                Volume vol = volumeList.get(i);
                if (vol.getHdID() == null) {
                    HardDrive hd = generateHardDrive(vol);
                    em.persist(hd);
                     em.flush();
//                     em.clear();
                    List<Volume> volumes = vol.getVolumes();
                    for (int j = 0; j < volumes.size(); j++) {
                        Partition pt = generatePartiton(volumes.get(j));
                        pt.setHardDrive(hd);
                        em.persist(pt);
                    }

                } else {
                    HardDrive hd = em.find(HardDrive.class, vol.getHdID());
                    // 一般不会发生 null， 极少发生，但是为了程序的健壮性，还是得加上验证
                    if (hd == null) {
                        hd = generateHardDrive(vol);
                        em.persist(hd);
                        em.flush();
                    } else {
                        hd.setNickname(vol.getNickname());
                        em.merge(hd);
                    }

                    List<Volume> volumes = vol.getVolumes();
                    for (int j = 0; j < volumes.size(); j++) {
                        Partition pt = em.find(Partition.class, volumes.get(j));
                        pt.setNickname(volumes.get(j).getNickname());
                        em.merge(pt);
                    }
                }
            }
            tran.commit();
            em.close();
            return new Result(true, "硬盘信息保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "硬盘信息保存失败");
        }

    }


}
