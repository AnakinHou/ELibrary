package org.tools.fx.library;

import java.util.List;
import javax.persistence.EntityManager;
import org.tools.fx.library.db.DBHelper;
import org.tools.fx.library.entity.HardDrive;
import org.tools.fx.library.entity.Partition;
import org.tools.fx.library.enums.PlatformEnum;
import org.tools.fx.library.model.Volume;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LoadVolumes {

    private static LoadVolumes loadHelper;

    private LoadVolumes() {

    }

    public static LoadVolumes getHelper() {
        if (loadHelper == null) {
            loadHelper = new LoadVolumes();
        }
        return loadHelper;
    }

    public TreeItem<Volume> loadVolumes() {
        Image hdActiveIcon = new Image(getClass().getResourceAsStream("/images/hd16active.png"));
        // 先去 硬盘表里面查询 所有的硬盘
        EntityManager em = DBHelper.getEM();
        List<HardDrive> hdList =
                em.createQuery(" select hd from HardDrive hd   ", HardDrive.class).getResultList();
        em.close();

        TreeItem<Volume> rootItem = new TreeItem<Volume>();
        // hdTreeTable.setRoot(rootItem);
        // hdTreeTable.setShowRoot(false);

        // 如果数据库中没有 硬盘信息，那就更省事了，直接返回当前 电脑连接的硬盘就好了
        if (hdList == null || hdList.size() == 0) {
            for (int i = 0; i < App.currentVolumes.size(); i++) {
                Volume volume = App.currentVolumes.get(i);
                TreeItem<Volume> diskTreeItem =
                        new TreeItem<Volume>(volume, new ImageView(hdActiveIcon));
                diskTreeItem.setExpanded(true);
                rootItem.getChildren().add(diskTreeItem);

                // 挂分区
                List<Volume> volumes = volume.getVolumes();
                for (Volume vlm : volumes) {
                    TreeItem<Volume> volumeTreeItem = new TreeItem<Volume>(vlm);
                    diskTreeItem.getChildren().add(volumeTreeItem);
                }
            }
        } else {
            Image hdInactiveIcon =
                    new Image(getClass().getResourceAsStream("/images/hd16inactive.png"));

            for (int i = 0; i < hdList.size(); i++) {
                HardDrive hd = hdList.get(i);
                Volume volume = new Volume(hd);
                TreeItem<Volume> diskTreeItem = null;
                // 如果 数据库中有硬盘，在 当前接入电脑的硬盘中存在，那么用 hd16active图片
                if (isExistInCurrentVolumes(volume.getHdUniqueCode())) {
                    diskTreeItem = new TreeItem<Volume>(volume, new ImageView(hdActiveIcon));
                } else {
                    diskTreeItem = new TreeItem<Volume>(volume, new ImageView(hdInactiveIcon));
                }
                diskTreeItem.setExpanded(true);
                rootItem.getChildren().add(diskTreeItem);

                // 挂分区
                List<Partition> ptList = hd.getPartitionList();
                for (int j = 0; j < ptList.size(); j++) {
                    Partition pt = ptList.get(j);
                    Volume vlm = new Volume(pt);
                    TreeItem<Volume> volumeTreeItem = new TreeItem<Volume>(vlm);
                    diskTreeItem.getChildren().add(volumeTreeItem);
                }
            }

            // 检查 当前硬盘列表是否在数据库中存在，不存在，那么添加
            for (int i = 0; i < App.currentVolumes.size(); i++) {
                Volume volume = App.currentVolumes.get(i);
                //// 如果当前硬盘 在数据库中不存在，那么 添加到列表
                HardDrive hd = findHDFromDBHardDrives(hdList, volume.getHdUniqueCode());
                // System.out.println("========== "+volume.getNickname() + ",
                // serialNumber:"+volume.getSerialNumber()+", mount: "+volume.getMountPoint());
                if (hd == null) {
                    TreeItem<Volume> diskTreeItem =
                            new TreeItem<Volume>(volume, new ImageView(hdActiveIcon));
                    diskTreeItem.setExpanded(true);
                    rootItem.getChildren().add(diskTreeItem);

                    // 挂分区
                    List<Volume> volumes = volume.getVolumes();
                    for (Volume vlm : volumes) {
                        TreeItem<Volume> volumeTreeItem = new TreeItem<Volume>(vlm);
                        diskTreeItem.getChildren().add(volumeTreeItem);
                    }
                } else {
                    // 如果已经存在，那么需要更新一下 App.currentVolumes 的 硬盘id 分区id
                    // System.out.println("========== 已经存在 更新 partition:" );
                    volume.setHdID(hd.getHdID());
                    List<Volume> volumes = volume.getVolumes();
                    List<Partition> ptList = hd.getPartitionList();
                    for (Volume vlm : volumes) {
                        vlm.setHdID(hd.getHdID());
                        Partition pt = findPTFromDBHardDrives(ptList, vlm.getUuid());
                        if (pt == null) {
                            continue;
                        }
                        vlm.setPtID(pt.getPtID());
                    }
                }
            }

        }
        return rootItem;
    }

    private boolean isExistInCurrentVolumes(String uniqueCode) {
        for (int i = 0; i < App.currentVolumes.size(); i++) {
            if (App.currentVolumes.get(i).getHdUniqueCode().equals(uniqueCode)) {
                return true;
            }
        }
        return false;
    }

    private HardDrive findHDFromDBHardDrives(List<HardDrive> hdList, String uniqueCode) {
        for (int i = 0; i < hdList.size(); i++) {
            if (hdList.get(i).getHdUniqueCode().equals(uniqueCode)) {
                return hdList.get(i);
            }
        }
        return null;
    }

    private Partition findPTFromDBHardDrives(List<Partition> ptList, String uuid) {
        if (App.os == PlatformEnum.WINDOWS) {
            for (int i = 0; i < ptList.size(); i++) {
                if (ptList.get(i).getWinUUID().equals(uuid)) {
                    return ptList.get(i);
                }
            }
        } else {
            for (int i = 0; i < ptList.size(); i++) {
                if (ptList.get(i).getMacUUID().equals(uuid)) {
                    return ptList.get(i);
                }
            }
        }
        return null;
    }

}
