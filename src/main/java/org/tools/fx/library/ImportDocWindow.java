package org.tools.fx.library;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.tools.fx.library.enums.PlatformEnum;
import org.tools.fx.library.model.FileImportRecord;
import org.tools.fx.library.model.Result;
import org.tools.fx.library.model.Volume;
import org.tools.fx.library.service.FileImportService;
import org.tools.fx.library.tools.FormatHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.ComboBoxTreeTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableColumn.CellEditEvent;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * 导入资料窗口
 * 
 * @author hydra
 *
 */
public class ImportDocWindow {

    // private ImageView folderIcon = new ImageView(new
    // Image(getClass().getResourceAsStream("/images/folder.png")));
    private Image folderIcon = new Image(getClass().getResourceAsStream("/images/folder.png"));
    private List<FileImportRecord> recordList = new LinkedList<>();

    public ImportDocWindow(Stage parentStage) {
        Stage importStage = new Stage();
        importStage.initOwner(parentStage);
        // 阻止事件传递到任何其他应用程序窗口
        importStage.initModality(Modality.APPLICATION_MODAL);
        // 阻止事件传递到所有者的窗口
        // s3.initModality(Modality.WINDOW_MODAL);
        importStage.getIcons().add(new Image("/images/logo.png"));
        
        VBox vbox = null;
        try {
            vbox = FXMLLoader.load(getClass().getResource("/fxml/ImportView.fxml"));
            Scene scene = new Scene(vbox);
            importStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        importStage.setTitle("选择 视频，音频，电子书等文件或文件夹");
        importStage.show();

        Button btnDirBrowse = (Button) vbox.lookup("#btn_dir_browse");
        Button btnFileBrowse = (Button) vbox.lookup("#btn_file_browse");

        Button btnOpenFile = (Button) vbox.lookup("#btn_open_file");
        Button btnDeleteFile = (Button) vbox.lookup("#btn_delete_file");
        // 提交按钮
        Button btnSubmitFiles = (Button) vbox.lookup("#btn_submit");
        TextField txtBrowse = (TextField) vbox.lookup("#txt_browse");

        TreeTableView<FileImportRecord> treeTableFileImport =
                (TreeTableView<FileImportRecord>) vbox.lookup("#ttv_file_import_record");

        ObservableList<TreeTableColumn<FileImportRecord, ?>> list =
                treeTableFileImport.getColumns();
        // for (int i = 0; i < list.size(); i++) {
        // TreeTableColumn<FileImportRecord, ?> col = list.get(i);
        // System.out.println(
        // "======*********************8 text " + col.getText() + " " + col.getId());
        // }
        // 文件名称
        TreeTableColumn<FileImportRecord, String> tcFileName =
                (TreeTableColumn<FileImportRecord, String>) getTreeColumnFormTreeTableView(list,
                        "tc_filename");
        // 文件标题"
        TreeTableColumn<FileImportRecord, String> tcSubtitle =
                (TreeTableColumn<FileImportRecord, String>) getTreeColumnFormTreeTableView(list,
                        "tc_subtitle");
        // 硬盘ID
        TreeTableColumn<FileImportRecord, Long> tcHDID =
                (TreeTableColumn<FileImportRecord, Long>) getTreeColumnFormTreeTableView(list,
                        "tc_hd_id");

        // 硬盘序列号
        TreeTableColumn<FileImportRecord, String> tcHDUniqueCode =
                (TreeTableColumn<FileImportRecord, String>) getTreeColumnFormTreeTableView(list,
                        "tc_hd_uniquecode");
        TreeTableColumn<FileImportRecord, Long> tcPTID =
                (TreeTableColumn<FileImportRecord, Long>) getTreeColumnFormTreeTableView(list,
                        "tc_pt_id");
        // 分区序列号
        TreeTableColumn<FileImportRecord, String> tcPTUUID =
                (TreeTableColumn<FileImportRecord, String>) getTreeColumnFormTreeTableView(list,
                        "tc_pt_uuid");
        // 挂载点
        TreeTableColumn<FileImportRecord, String> tcMountPoint =
                (TreeTableColumn<FileImportRecord, String>) getTreeColumnFormTreeTableView(list,
                        "tc_mount_point");
        // 文件路径
        TreeTableColumn<FileImportRecord, String> tcFilePath =
                (TreeTableColumn<FileImportRecord, String>) getTreeColumnFormTreeTableView(list,
                        "tc_file_path");
        // 文件大小
        TreeTableColumn<FileImportRecord, String> tcFileSize =
                (TreeTableColumn<FileImportRecord, String>) getTreeColumnFormTreeTableView(list,
                        "tc_file_size");
        // 最后修改时间
        TreeTableColumn<FileImportRecord, String> tcLastModified =
                (TreeTableColumn<FileImportRecord, String>) getTreeColumnFormTreeTableView(list,
                        "tc_last_modified_time");
        // 类型大分类
        TreeTableColumn<FileImportRecord, String> tcFileMainType =
                (TreeTableColumn<FileImportRecord, String>) getTreeColumnFormTreeTableView(list,
                        "tc_file_main_type");
        // 文件标记
        TreeTableColumn<FileImportRecord, String> tcFileMark =
                (TreeTableColumn<FileImportRecord, String>) getTreeColumnFormTreeTableView(list,
                        "tc_file_mark");
        // 文件备注
        TreeTableColumn<FileImportRecord, String> tcFileTip =
                (TreeTableColumn<FileImportRecord, String>) getTreeColumnFormTreeTableView(list,
                        "tc_file_tip");
        // 后缀名
        TreeTableColumn<FileImportRecord, String> tcFileSuffix =
                (TreeTableColumn<FileImportRecord, String>) getTreeColumnFormTreeTableView(list,
                        "tc_file_suffix");

        TreeTableColumn<FileImportRecord, String> tcPlatform =
                (TreeTableColumn<FileImportRecord, String>) getTreeColumnFormTreeTableView(list,
                        "tc_platform");

        tcFileName.setCellValueFactory(
                new TreeItemPropertyValueFactory<FileImportRecord, String>("fileName"));
        tcSubtitle.setCellValueFactory(
                new TreeItemPropertyValueFactory<FileImportRecord, String>("subtitle"));
        tcHDID.setCellValueFactory(
                new TreeItemPropertyValueFactory<FileImportRecord, Long>("hdID"));
        tcHDUniqueCode.setCellValueFactory(
                new TreeItemPropertyValueFactory<FileImportRecord, String>("hdUniqueCode"));
        tcPTID.setCellValueFactory(
                new TreeItemPropertyValueFactory<FileImportRecord, Long>("ptID"));
        tcPTUUID.setCellValueFactory(
                new TreeItemPropertyValueFactory<FileImportRecord, String>("ptUUID"));
        tcMountPoint.setCellValueFactory(
                new TreeItemPropertyValueFactory<FileImportRecord, String>("mountPoint"));
        tcFilePath.setCellValueFactory(
                new TreeItemPropertyValueFactory<FileImportRecord, String>("filePath"));
        // tcFileSize.setCellValueFactory(
        // new TreeItemPropertyValueFactory<FileImportRecord, Long>("fileSize"));
        // tcLastModified.setCellValueFactory(
        // new TreeItemPropertyValueFactory<FileImportRecord, Long>("lastModified"));

        tcFileSize.setCellValueFactory(
                new Callback<TreeTableColumn.CellDataFeatures<FileImportRecord, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(
                            CellDataFeatures<FileImportRecord, String> param) {
                        return new SimpleStringProperty(
                                FormatHelper.format(param.getValue().getValue().getFileSize()));
                    }
                });
        tcLastModified.setCellValueFactory(
                new Callback<TreeTableColumn.CellDataFeatures<FileImportRecord, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(
                            CellDataFeatures<FileImportRecord, String> param) {
                        return new SimpleStringProperty(FormatHelper
                                .formatDate(param.getValue().getValue().getLastModified()));
                    }
                });

        tcFileMainType.setCellValueFactory(
                new TreeItemPropertyValueFactory<FileImportRecord, String>("fileMainType"));
        tcFileMark.setCellValueFactory(
                new TreeItemPropertyValueFactory<FileImportRecord, String>("fileMark"));
        tcFileTip.setCellValueFactory(
                new TreeItemPropertyValueFactory<FileImportRecord, String>("fileTip"));
        tcFileSuffix.setCellValueFactory(
                new TreeItemPropertyValueFactory<FileImportRecord, String>("fileSuffix"));
        tcPlatform.setCellValueFactory(
                new TreeItemPropertyValueFactory<FileImportRecord, String>("platform"));

        tcSubtitle.setCellFactory(
                new Callback<TreeTableColumn<FileImportRecord, String>, TreeTableCell<FileImportRecord, String>>() {
                    @Override
                    public TreeTableCell<FileImportRecord, String> call(
                            TreeTableColumn<FileImportRecord, String> param) {
                        return new TextFieldTreeTableCell<FileImportRecord, String>(
                                new StringConverter<String>() {
                                    @Override
                                    public String toString(String object) {
                                        return object;
                                    }

                                    @Override
                                    public String fromString(String string) {
                                        return string;
                                    }
                                }) {
                            @Override
                            public void startEdit() {
                                if (getTreeTableView().getSelectionModel().getSelectedItem()
                                        .getValue().isFile()) {
                                    super.startEdit();
                                    // } else {
                                    // System.out.println("====== 文件夹 不可以编辑");
                                }
                            }
                        };
                    }
                });
        tcSubtitle.setOnEditCommit(
                new EventHandler<TreeTableColumn.CellEditEvent<FileImportRecord, String>>() {
                    @Override
                    public void handle(CellEditEvent<FileImportRecord, String> event) {
                        event.getRowValue().getValue().setSubtitle(event.getNewValue());
                    }
                });
        tcFileMainType.setCellFactory(
                new Callback<TreeTableColumn<FileImportRecord, String>, TreeTableCell<FileImportRecord, String>>() {

                    @Override
                    public TreeTableCell<FileImportRecord, String> call(
                            TreeTableColumn<FileImportRecord, String> param) {
                        return new ComboBoxTreeTableCell<FileImportRecord, String>("文档", "视频", "音乐",
                                "图片", "软件") {
                            @Override
                            public void startEdit() {
                                if (getTreeTableView().getSelectionModel().getSelectedItem()
                                        .getValue().isFile()) {
                                    super.startEdit();
                                }
                            }
                        };
                    }
                });
        tcFileMainType.setOnEditCommit(
                new EventHandler<TreeTableColumn.CellEditEvent<FileImportRecord, String>>() {
                    @Override
                    public void handle(CellEditEvent<FileImportRecord, String> event) {
                        event.getRowValue().getValue().setFileMainType(event.getNewValue());
                    }
                });


        tcFileMark.setCellFactory(
                new Callback<TreeTableColumn<FileImportRecord, String>, TreeTableCell<FileImportRecord, String>>() {

                    @Override
                    public TreeTableCell<FileImportRecord, String> call(
                            TreeTableColumn<FileImportRecord, String> param) {
                        return new ComboBoxTreeTableCell<FileImportRecord, String>("学习", "工作",
                                "爱好") {
                            @Override
                            public void startEdit() {
                                if (getTreeTableView().getSelectionModel().getSelectedItem()
                                        .getValue().isFile()) {
                                    super.startEdit();
                                }
                            }
                        };
                    }
                });
        tcFileMark.setOnEditCommit(
                new EventHandler<TreeTableColumn.CellEditEvent<FileImportRecord, String>>() {
                    @Override
                    public void handle(CellEditEvent<FileImportRecord, String> event) {
                        event.getRowValue().getValue().setFileMark(event.getNewValue());
                    }
                });

        tcFileTip.setCellFactory(
                new Callback<TreeTableColumn<FileImportRecord, String>, TreeTableCell<FileImportRecord, String>>() {

                    @Override
                    public TreeTableCell<FileImportRecord, String> call(
                            TreeTableColumn<FileImportRecord, String> param) {
                        return new TextFieldTreeTableCell<FileImportRecord, String>(
                                new StringConverter<String>() {
                                    @Override
                                    public String toString(String object) {
                                        return object;
                                    }

                                    @Override
                                    public String fromString(String string) {
                                        return string;
                                    }
                                }) {

                            @Override
                            public void startEdit() {
                                if (getTreeTableView().getSelectionModel().getSelectedItem()
                                        .getValue().isFile()) {
                                    super.startEdit();
                                }
                            }
                        };
                    }
                });
        tcFileTip.setOnEditCommit(
                new EventHandler<TreeTableColumn.CellEditEvent<FileImportRecord, String>>() {
                    @Override
                    public void handle(CellEditEvent<FileImportRecord, String> event) {
                        event.getRowValue().getValue().setFileTip(event.getNewValue());
                    }
                });
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择文件");

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择文件夹");

        // String dirInitialDirectory = directoryChooser.getInitialDirectory().getAbsolutePath();
        // String fileInitialDirectory = fileChooser.getInitialDirectory().getAbsolutePath();
        // System.out.println("==== dirInitialDirectory:" + dirInitialDirectory);
        // System.out.println("==== fileInitialDirectory:" + fileInitialDirectory);

        /**
         * 浏览文件夹
         */
        btnDirBrowse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // directoryChooser.setInitialDirectory(new File(dirInitialDirectory));
                File directory = directoryChooser.showDialog(importStage);
                if (directory != null) {
                    // dirInitialDirectory = directory.getAbsolutePath();
                    // System.out.println(directory.getAbsolutePath());
                    txtBrowse.setText(directory.getAbsolutePath());
                    loadFileToTreeTable(treeTableFileImport, directory.listFiles());
                }
            }
        });

        /**
         * 浏览文件 可多选
         */
        btnFileBrowse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // fileChooser.setInitialDirectory(new File(fileInitialDirectory));
                // System.out.println(fileInitialDirectory);
                List<File> fileList = fileChooser.showOpenMultipleDialog(importStage);
                // System.out.println(file.getAbsolutePath());
                if (fileList != null && fileList.size() > 0) {
                    txtBrowse.setText(fileList.get(0).getParent());
                    File[] files = new File[fileList.size()];
                    fileList.toArray(files);
                    loadFileToTreeTable(treeTableFileImport, files);
                }
            }
        });

        /**
         * 打开文件
         */
        btnOpenFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TreeItem<FileImportRecord> item =
                        treeTableFileImport.getSelectionModel().getSelectedItem();
                // Desktop.getDesktop().browse(File.getParentFile().toURI());
                if (item != null) {
                    FileImportRecord record = item.getValue();
                    File f = new File(record.getMountPoint() + record.getFilePath());
                    try {
                        if (record.isFile()) {
                            // Desktop.getDesktop().browse(f.getParentFile().toURI());
                            Desktop.getDesktop().open(f.getParentFile());
                        } else {
                            // Desktop.getDesktop().browse(f.toURI());
                            Desktop.getDesktop().open(f);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        /**
         * 删除文件
         */
        btnDeleteFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TreeItem<FileImportRecord> item =
                        treeTableFileImport.getSelectionModel().getSelectedItem();
                if (item != null) {
                    // treeTableFileImport.getRoot().getChildren().remove(item);
                    if (item.getValue().isFile()) {
                        for (int i = 0; i < recordList.size(); i++) {
                            if (recordList.get(i).getFilePath()
                                    .equals(item.getValue().getFilePath())) {
                                recordList.remove(i);
                                break;
                            }
                        }

                        item.getParent().getChildren().remove(item);
                    } else {
                        // Alert alert = new Alert(AlertType.WARNING);
                        // alert.setTitle("警告");
                        // alert.setHeaderText("不允许删除文件夹");
                        // alert.setContentText("请删除文件夹下的文件!");
                        // alert.showAndWait();

                        // Alert alert = new Alert(AlertType.CONFIRMATION);
                        // 按钮部分可以使用预设的也可以像这样自己 new 一个
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "确定要删除文件夹吗？",
                                new ButtonType("取消", ButtonData.NO),
                                new ButtonType("确定", ButtonData.YES));
                        // 设置窗口的标题
                        alert.setTitle("删除文件夹");
                        alert.setHeaderText("当前操作只会在该列表删除文件，并不会在硬盘上删除文件");
                        // 设置对话框的 icon 图标，参数是主窗口的 stage
                        alert.initOwner(importStage);
                        // showAndWait() 将在对话框消失以前不会执行之后的代码
                        Optional<ButtonType> buttonType = alert.showAndWait();
                        // 根据点击结果返回
                        if (buttonType.get().getButtonData().equals(ButtonData.YES)) {
                            item.getParent().getChildren().remove(item);
                            deleteFileFromTreeItem(item);
                        }

                    }
                }
            }
        });
        /**
         * 提交 treetable 中的文件信息到数据库
         */
        btnSubmitFiles.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // treeTableFileImport. accessibleHelpProperty()
                // System.out.println("===== recordList.size() " + recordList.size());
                // for (int i = 0; i < recordList.size(); i++) {
                // FileImportRecord fileRecord = recordList.get(i);
                // System.out.println(fileRecord.getHdID() + " , " + fileRecord.getPtID()
                // + fileRecord.getFileName() + ", fileSize:" + fileRecord.getFileSize()
                // + ", lastModified:" + fileRecord.getLastModified() + ", fileMaintype:"
                // + fileRecord.getFileMainType() + ", fileMark:"
                // + fileRecord.getFileMark());
                // }
                Result result = FileImportService.getService().importFile(recordList);
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("信息");
                alert.setHeaderText(result.getMessage());
                // alert.setContentText("保存成功!");
                alert.showAndWait();
            }
        });
    }


    private TreeTableColumn<FileImportRecord, ?> getTreeColumnFormTreeTableView(
            ObservableList<TreeTableColumn<FileImportRecord, ?>> list, String colID) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(colID)) {
                return list.get(i);
            }
        }
        return null;
    }


    private void deleteFileFromTreeItem(TreeItem<FileImportRecord> item) {
        ObservableList<TreeItem<FileImportRecord>> children = item.getChildren();
        for (int z = 0; z < children.size(); z++) {
            TreeItem<FileImportRecord> node = children.get(z);
            if (node.getValue().isFile()) {
                for (int i = 0; i < recordList.size(); i++) {
                    System.out.println("===== delete file path:"+node.getValue().getFilePath());
                    if (recordList.get(i).getFilePath().equals(node.getValue().getFilePath())) {
                        recordList.remove(i);
                        break;
                    }
                }
            } else {
                deleteFileFromTreeItem(node);
            }
        }
    }

    /**
     * 根据文件路径，查找当前电脑已连接硬盘的分区 如果 该分区 数据库中没有，那么不能选择文件
     * 
     * @param folderPath
     * @return
     */
    private Volume getVolumeFormCurrentVolumes(String folderPath) {
        for (int i = 0; i < App.currentVolumes.size(); i++) {
            // 硬盘跳过
            List<Volume> partitons = App.currentVolumes.get(i).getVolumes();
            for (int j = 0; j < partitons.size(); j++) {
                if (partitons.get(j).getMountPoint().equals("/")) {
                    continue;
                }
                if (folderPath.startsWith(partitons.get(j).getMountPoint())) {
                    return partitons.get(j);
                }
            }
        }
        return null;
    }



    /**
     * macOS 系统根目录
     * 
     * @return
     */
    private Volume getRootVolumeFormCurrentVolumes() {
        for (int i = 0; i < App.currentVolumes.size(); i++) {
            // 硬盘跳过
            List<Volume> partitons = App.currentVolumes.get(i).getVolumes();
            for (int j = 0; j < partitons.size(); j++) {
                System.out.println(partitons.get(j).getMountPoint());
                if (partitons.get(j).getMountPoint().equals("/")) {
                    return partitons.get(j);
                }
            }
            // System.out.println("======= partitons.get(i).getMountPoint():"
            // + partitons.get(i).getMountPoint());
        }
        return null;
    }

    /**
     * 
     * @param files
     */
    private void loadFileToTreeTable(TreeTableView<FileImportRecord> treeTableFileImport,
            File... files) {
        if (files.length > 0) {
            // List<Volume> volumes = App.currentVolumes;
            // for (Volume volume : volumes) {
            // System.out.print(volume);
            // System.out.println(volume.getSerialNumber() + " " + volume.getUuid());
            // }

            // 根据 所选的文件， 获取硬盘序列号信息
            File parentFolder = files[0].getParentFile();
            String folderPath = parentFolder.getAbsolutePath();
            // System.out.println("===== folderPath:" + folderPath);
            // 根据 parentFolder 获取文件所在的硬盘信息
            String hdUniqueCode = null;
            String hdNickname = null;
            String ptUUID = null;
            String mountPoint = null;
            String platform = null;
            Long hdID = null;
            Long ptID = null;

            if (App.os == PlatformEnum.WINDOWS) {
                platform = PlatformEnum.WINDOWS.toString();
                System.out.println("============ folderPath:" + folderPath);
                Volume vlm = getVolumeFormCurrentVolumes(folderPath);
                if (vlm == null || vlm.getHdID() == null) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("警告");
                    alert.setHeaderText("无法选择这些文件");
                    alert.setContentText("文件所在的硬盘或分区，还没有保存到数据库!");
                    alert.show();
                    return;
                }
                hdID = vlm.getHdID();
                ptID = vlm.getPtID();
                hdUniqueCode = vlm.getHdUniqueCode();
                hdNickname = vlm.getNickname();
                ptUUID = vlm.getUuid();
                mountPoint = vlm.getMountPoint()+"\\";
                System.out.println("==== hdID:"+hdID);
                System.out.println("======= ptID:"+ptID);
                System.out.println("========== hdUniqueCode:"+hdUniqueCode);
                System.out.println("============== hdNickname:"+hdNickname); 
                System.out.println("================= ptUUID:"+ptUUID); 
                System.out.println("==================== mountPoint:"+mountPoint); 
            } else {
                platform = PlatformEnum.MACOS.toString();
                // 挂载点 类似这样 /Volumes/Others
                if (folderPath.startsWith("/Volumes")) {
                    Volume vlm = getVolumeFormCurrentVolumes(folderPath);
                    if (vlm == null || vlm.getHdID() == null) {
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setTitle("警告");
                        alert.setHeaderText("无法选择这些文件");
                        alert.setContentText("文件所在的硬盘或分区，还没有保存到数据库!");
                        alert.show();
                        return;
                    }
                    hdID = vlm.getHdID();
                    ptID = vlm.getPtID();
                    hdUniqueCode = vlm.getHdUniqueCode();
                    hdNickname = vlm.getNickname();
                    ptUUID = vlm.getUuid();
                    mountPoint = vlm.getMountPoint();
                } else {
                    // 这明显是 mac系统的硬盘 因为只有一个 /
                    Volume vlm = getRootVolumeFormCurrentVolumes();
                    // System.out.println("====nickname:" + vlm.getNickname() + ", hdID:"
                    // + vlm.getHdID() + ", ptID:" + vlm.getPtID() + ", SerialNumber:"
                    // + vlm.getSerialNumber() + ", UUID:" + vlm.getUuid());
                    if (vlm == null || vlm.getHdID() == null) {
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setTitle("警告");
                        alert.setHeaderText("无法选择这些文件");
                        alert.setContentText("文件所在的硬盘或分区，还没有保存到数据库!");
                        alert.show();
                        return;
                    }
                    hdID = vlm.getHdID();
                    ptID = vlm.getPtID();
                    hdUniqueCode = vlm.getHdUniqueCode();
                    hdNickname = vlm.getNickname();
                    ptUUID = vlm.getUuid();
                    mountPoint = "/";
                }
            }

            // 先创建一个根结点
            FileImportRecord rootFI = new FileImportRecord(parentFolder);
            rootFI.setHdUniqueCode(hdUniqueCode);
            rootFI.setHdID(hdID);
            TreeItem<FileImportRecord> itemRoot = new TreeItem<FileImportRecord>(rootFI);
            itemRoot.setExpanded(true);

            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    if (files[i].getName().equals(".DS_Store")
                            || files[i].getName().equals(".localized")) {
                        continue;
                    }
                    FileImportRecord fi = new FileImportRecord(files[i]);
                    fi.setFilePath(fi.getFilePath().replaceFirst(mountPoint, ""));
                    fi.setHdID(hdID);
                    fi.setPtID(ptID);
                    fi.setPtUUID(ptUUID);
                    fi.setHdUniqueCode(hdUniqueCode);
                    fi.setHdNickname(hdNickname);
                    fi.setMountPoint(mountPoint);
                    fi.setPlatform(platform);
                    recordList.add(fi);
                    TreeItem<FileImportRecord> item = new TreeItem<FileImportRecord>(fi);
                    itemRoot.getChildren().add(item);
                } else {
                    TreeItem<FileImportRecord> folderItem = loadMoreFiles(recordList, files[i],
                            hdUniqueCode, hdNickname, hdID, ptID, ptUUID, mountPoint, platform);
                    itemRoot.getChildren().add(folderItem);
                }
            }
            treeTableFileImport.setRoot(itemRoot);
            treeTableFileImport.setShowRoot(false);
        }
    }

    private TreeItem<FileImportRecord> loadMoreFiles(List<FileImportRecord> recordList, File folder,
            String hdUniqueCode, String hdNickname, Long hdID, Long ptID, String ptUUID,
            String mountPoint, String platform) {
        FileImportRecord folderRecord = new FileImportRecord(folder);
        TreeItem<FileImportRecord> parentfolderItem =
                new TreeItem<FileImportRecord>(folderRecord, new ImageView(folderIcon));
        File[] files = folder.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                if (files[i].getName().equals(".DS_Store") || files[i].getName().equals("._.DS_Store")
                        || files[i].getName().equals(".localized")) {
                    continue;
                }
                FileImportRecord fi = new FileImportRecord(files[i]);
                fi.setFilePath(fi.getFilePath().replaceFirst(mountPoint, ""));
                fi.setHdUniqueCode(hdUniqueCode);
                fi.setHdNickname(hdNickname);
                fi.setHdID(hdID);
                fi.setPtID(ptID);
                fi.setPtUUID(ptUUID);
                fi.setMountPoint(mountPoint);
                fi.setPlatform(platform);
                recordList.add(fi);
                TreeItem<FileImportRecord> fileItem = new TreeItem<FileImportRecord>(fi);
                parentfolderItem.getChildren().add(fileItem);
            } else {
                TreeItem<FileImportRecord> folderItem = loadMoreFiles(recordList, files[i],
                        hdUniqueCode, hdNickname, hdID, ptID, ptUUID, mountPoint, platform);
                // folderItem.setGraphic(folderIcon);
                parentfolderItem.getChildren().add(folderItem);
            }
        }
        return parentfolderItem;
    }


}
