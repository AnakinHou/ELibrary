package org.tools.fx.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import org.tools.fx.library.db.DBHelper;
import org.tools.fx.library.entity.HardDrive;
import org.tools.fx.library.entity.Partition;
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
            List<Volume> volumeNode = mergeDBHardDriveAndCurrentVolumes(hdList, App.currentVolumes);
            for (int i = 0; i < volumeNode.size(); i++) {
                Volume hdvlm = volumeNode.get(i);
                TreeItem<Volume> diskTreeItem = null;
                // 如果 数据库中有硬盘，在 当前接入电脑的硬盘中存在，那么用 hd16active图片
                if (hdvlm.isActive()) {
                    diskTreeItem = new TreeItem<Volume>(hdvlm, new ImageView(hdActiveIcon));
                } else {
                    diskTreeItem = new TreeItem<Volume>(hdvlm, new ImageView(hdInactiveIcon));
                }
                diskTreeItem.setExpanded(true);
                rootItem.getChildren().add(diskTreeItem);
                
                List<Volume> ptList = hdvlm.getVolumes();
                for (int j = 0; j < ptList.size(); j++) {
                    Volume vlm = ptList.get(j);
                    TreeItem<Volume> volumeTreeItem = new TreeItem<Volume>(vlm);
                    diskTreeItem.getChildren().add(volumeTreeItem);
                }
            }
        }
        return rootItem;
    }



    private List<Volume> mergeDBHardDriveAndCurrentVolumes(List<HardDrive> hdList,
            List<Volume> currentVolumes) {
        HashMap<String, HardDrive> hdMap = new HashMap<String, HardDrive>(hdList.size());
        HashMap<String, Volume> volumeMap = new HashMap<String, Volume>(currentVolumes.size());

        List<Volume> volumeNode = new ArrayList<Volume>();
        List<Volume> newVolumes = new ArrayList<Volume>();

        for (HardDrive hd : hdList) {
            hdMap.put(hd.getHdUniqueCode(), hd);
        }

        for (Volume volume : currentVolumes) {
            volumeMap.put(volume.getHdUniqueCode(), volume);
            // 如果当前 挂载的 硬盘中，在数据库中不存在，那么说明是新硬盘
            if (!hdMap.containsKey(volume.getHdUniqueCode())) {
                newVolumes.add(volume);
            }
        }

        for (HardDrive dbHD : hdList) {
            Volume hdVLM = new Volume(dbHD);
            volumeNode.add(hdVLM);
            /// 如果 数据库中的硬盘，在电脑当前挂在的硬盘中不存在
            // 那就说明，硬盘，没有接入电脑
            Volume pluginHD = volumeMap.get(dbHD.getHdUniqueCode());
            if (pluginHD == null) {
                hdVLM.setActive(false);

                List<Partition> dbptList = dbHD.getPartitionList();
                for (int i = 0; i < dbptList.size(); i++) {
                    hdVLM.getVolumes().add(new Volume(dbptList.get(i)));
                }
                continue;
            }

            // 如果 数据库中的硬盘，在当前电脑 接入的硬盘里，存在
            // 那说明 该硬盘 接入了当前电脑 active = true
            hdVLM.setActive(true);
            // pluginHD.setvType(1);
            pluginHD.setNickname(hdVLM.getNickname());
            pluginHD.setHdID(hdVLM.getHdID());

            // 接下来merge 分区
            List<Partition> dbptList = dbHD.getPartitionList();
            List<Volume> pluginPtList = pluginHD.getVolumes();
            // HashMap<String, Partition> dbptMap = new HashMap<String, Partition>(dbptList.size());
            HashMap<String, Volume> pluginPtMap = new HashMap<String, Volume>(pluginPtList.size());
            for (Volume volume : pluginPtList) {
                pluginPtMap.put(volume.getUuid(), volume);
            }

            for (Partition pt : dbptList) {
                Volume ptVLM = new Volume(pt);

                Volume pluginVLM = pluginPtMap.get(ptVLM.getUuid());
                pluginVLM.setHdID(dbHD.getHdID());
                pluginVLM.setPtID(pt.getPtID());
                pluginVLM.setNickname(pt.getNickname());
                ptVLM.setMountPoint(pluginVLM.getMountPoint());
                hdVLM.getVolumes().add(ptVLM);
            }
        }

        volumeNode.addAll(newVolumes);
        return volumeNode;
    }



}
