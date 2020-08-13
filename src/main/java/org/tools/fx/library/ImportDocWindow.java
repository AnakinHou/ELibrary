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
import org.tools.fx.library.widgets.CheckBoxTreeTableRow;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.CheckBoxTreeItem;
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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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
    private Image fileIcon = new Image(getClass().getResourceAsStream("/images/file16.png"));
    // private Image folderIcon = new Image(getClass().getResource("/images/folder.png").getPath());
    // private List<FileImportRecord> recordList = new LinkedList<>();

    public ImportDocWindow(Stage parentStage) {
        Stage importStage = new Stage();
        importStage.initOwner(parentStage);
        // 阻止事件传递到任何其他应用程序窗口
        importStage.initModality(Modality.APPLICATION_MODAL);
        // 阻止事件传递到所有者的窗口
        // s3.initModality(Modality.WINDOW_MODAL);
        importStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/Logo.png")));
        // importStage.getIcons().add(new
        // Image(ImportDocWindow.class.getResourceAsStream("/images/logo.png")));
        // importStage.getIcons().add(new
        // Image(getClass().getResource("/images/logo.png").getPath()));

        VBox vbox = new VBox();
        vbox.setPrefHeight(600.0d);
        vbox.setPrefWidth(1000.0d);
        vbox.setMaxSize(1280.0d, 720.0d);

        ToolBar toolBar = new ToolBar();
        toolBar.setPrefHeight(40.0d);

        TextField txtBrowse = new TextField();
        txtBrowse.setPrefHeight(30.0d);
        txtBrowse.setPrefWidth(600.0d);
        txtBrowse.setPromptText("请选择文件夹或文件");
        txtBrowse.setFont(Font.font("System Bold", 14.0d));

        Button btnDirBrowse = new Button("浏览文件夹");
        btnDirBrowse.setMnemonicParsing(false);
        btnDirBrowse.setPrefHeight(30.0d);
        btnDirBrowse.setFont(Font.font("System Bold", 14.0d));

        Button btnFileBrowse = new Button("浏览文件");
        btnFileBrowse.setMnemonicParsing(false);
        btnFileBrowse.setPrefHeight(30.0d);
        btnFileBrowse.setFont(Font.font("System Bold", 14.0d));

        Button btnOpenFile = new Button("打开文件");
        btnOpenFile.setMnemonicParsing(false);
        btnOpenFile.setPrefHeight(30.0d);
        // btnOpenFile.setDefaultButton(true);
        btnOpenFile.setFont(Font.font("System Bold", 14.0d));

        // Button btnDeleteFile = new Button("删除");
        // btnDeleteFile.setMnemonicParsing(false);
        // btnDeleteFile.setPrefHeight(30.0d);
        // btnDeleteFile.setCancelButton(true);
        // btnDeleteFile.setFont(Font.font("System Bold", 14.0d));

        toolBar.getItems().addAll(txtBrowse, btnDirBrowse, btnFileBrowse, btnOpenFile);

        TreeTableView<FileImportRecord> treeTableView = new TreeTableView<FileImportRecord>();
        treeTableView.setEditable(true);
        // treeTableView VBox.vgrow="ALWAYS"
        treeTableView.setRowFactory(item -> new CheckBoxTreeTableRow<>());

        // 文件名称
        TreeTableColumn<FileImportRecord, String> tcFileName =
                new TreeTableColumn<FileImportRecord, String>("名称");
        tcFileName.setPrefWidth(240d);
        tcFileName.setMinWidth(140d);
        // 文件标题"
        TreeTableColumn<FileImportRecord, String> tcSubtitle =
                new TreeTableColumn<FileImportRecord, String>("文件标题");
        tcSubtitle.setPrefWidth(240d);
        tcSubtitle.setMinWidth(120d);
        // 硬盘ID
        TreeTableColumn<FileImportRecord, Long> tcHDID =
                new TreeTableColumn<FileImportRecord, Long>("HDID");
        tcHDID.setPrefWidth(80d);
        // 硬盘序列号
        TreeTableColumn<FileImportRecord, String> tcHDUniqueCode =
                new TreeTableColumn<FileImportRecord, String>("硬盘识别码");
        tcHDUniqueCode.setPrefWidth(220d);

        TreeTableColumn<FileImportRecord, Long> tcPTID =
                new TreeTableColumn<FileImportRecord, Long>("PT_ID");
        tcPTID.setPrefWidth(80d);
        // 分区序列号
        TreeTableColumn<FileImportRecord, String> tcPTUUID =
                new TreeTableColumn<FileImportRecord, String>("分区序列号");
        tcPTUUID.setPrefWidth(200d);
        // 挂载点
        TreeTableColumn<FileImportRecord, String> tcMountPoint =
                new TreeTableColumn<FileImportRecord, String>("挂载点");
        tcMountPoint.setPrefWidth(80d);
        // 文件路径
        TreeTableColumn<FileImportRecord, String> tcFilePath =
                new TreeTableColumn<FileImportRecord, String>("文件路径");
        tcFilePath.setPrefWidth(200d);
        // 文件大小
        TreeTableColumn<FileImportRecord, String> tcFileSize =
                new TreeTableColumn<FileImportRecord, String>("文件大小");
        tcFileSize.setPrefWidth(100d);
        // 最后修改时间
        TreeTableColumn<FileImportRecord, String> tcLastModified =
                new TreeTableColumn<FileImportRecord, String>("最后修改时间");
        tcLastModified.setPrefWidth(128d);
        // 类型大分类
        TreeTableColumn<FileImportRecord, String> tcFileMainType =
                new TreeTableColumn<FileImportRecord, String>("类型大分类");
        tcFileMainType.setPrefWidth(80d);
        // 文件标记
        TreeTableColumn<FileImportRecord, String> tcFileMark =
                new TreeTableColumn<FileImportRecord, String>("文件标记");
        tcFileMark.setPrefWidth(80d);
        // 文件备注
        TreeTableColumn<FileImportRecord, String> tcFileTip =
                new TreeTableColumn<FileImportRecord, String>("文件备注");
        tcFileTip.setPrefWidth(210d);
        // 后缀名
        TreeTableColumn<FileImportRecord, String> tcFileSuffix =
                new TreeTableColumn<FileImportRecord, String>("后缀名");
        tcFileSuffix.setPrefWidth(60d);

        TreeTableColumn<FileImportRecord, String> tcPlatform =
                new TreeTableColumn<FileImportRecord, String>("系统平台");
        tcPlatform.setPrefWidth(60d);

        treeTableView.getColumns().addAll(tcFileName, tcSubtitle, tcHDID, tcHDUniqueCode, tcPTID,
                tcPTUUID, tcMountPoint, tcFilePath, tcFileSize, tcLastModified, tcFileMainType,
                tcFileMark, tcFileTip, tcFileSuffix, tcPlatform);


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
        tcFileSize.setCellValueFactory(
                new TreeItemPropertyValueFactory<FileImportRecord, String>("fileSize"));
        tcLastModified.setCellValueFactory(
                new TreeItemPropertyValueFactory<FileImportRecord, String>("lastModified"));

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

        tcFileName.setCellFactory(tcell -> new TreeTableCell<FileImportRecord, String>() {

            @Override
            protected void updateItem(String item, boolean empty) {

                if (item == getItem())
                    return;

                super.updateItem(item, empty);
                if (item == null) {
                    super.setText(null);
                    super.setGraphic(null);
                } else {
                    super.setText("      " + item.toString());
                    super.setGraphic(null);
                }
            }
        });

        FlowPane bottomPane = new FlowPane(Orientation.HORIZONTAL);
        bottomPane.setMaxHeight(40.0d);
        bottomPane.setAlignment(Pos.CENTER_RIGHT);
        // bottomPane.setvg

        Button btnSubmitFiles = new Button("提交");
        btnSubmitFiles.setAlignment(Pos.CENTER);
        btnSubmitFiles.setContentDisplay(ContentDisplay.CENTER);
        btnSubmitFiles.setDefaultButton(true);
        btnSubmitFiles.setMnemonicParsing(true);
        btnSubmitFiles.setPrefHeight(30.0d);
        btnSubmitFiles.setPrefWidth(110.0d);
        btnSubmitFiles.setMaxWidth(120.0d);
        btnSubmitFiles.setFont(Font.font("System Bold", 14.0d));
        FlowPane.setMargin(btnSubmitFiles, new Insets(0.0d, 50d, 0d, 0d));
        // bottomPane.setHgap(50d);
        bottomPane.getChildren().add(btnSubmitFiles);

        vbox.getChildren().addAll(toolBar, treeTableView, bottomPane);
        VBox.setVgrow(treeTableView, Priority.ALWAYS);
        VBox.setVgrow(bottomPane, Priority.ALWAYS);


        Scene scene = new Scene(vbox);
        importStage.setScene(scene);


        importStage.setTitle("选择 视频，音频，电子书等文件或文件夹");
        importStage.show();

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
                    loadFileToTreeTable(treeTableView, directory.listFiles());
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
                    loadFileToTreeTable(treeTableView, files);
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
                        treeTableView.getSelectionModel().getSelectedItem();
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
        // btnDeleteFile.setOnAction(new EventHandler<ActionEvent>() {
        // @Override
        // public void handle(ActionEvent event) {
        // TreeItem<FileImportRecord> item =
        // treeTableView.getSelectionModel().getSelectedItem();
        // if (item != null) {
        // // treeTableFileImport.getRoot().getChildren().remove(item);
        // if (item.getValue().isFile()) {
        // for (int i = 0; i < recordList.size(); i++) {
        // if (recordList.get(i).getFilePath()
        // .equals(item.getValue().getFilePath())) {
        // recordList.remove(i);
        // break;
        // }
        // }
        // item.getParent().getChildren().remove(item);
        // } else {
        // // Alert alert = new Alert(AlertType.WARNING);
        // // alert.setTitle("警告");
        // // alert.setHeaderText("不允许删除文件夹");
        // // alert.setContentText("请删除文件夹下的文件!");
        // // alert.showAndWait();
        //
        // // Alert alert = new Alert(AlertType.CONFIRMATION);
        // // 按钮部分可以使用预设的也可以像这样自己 new 一个
        // Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "确定要删除文件夹吗？",
        // new ButtonType("取消", ButtonData.NO),
        // new ButtonType("确定", ButtonData.YES));
        // // 设置窗口的标题
        // alert.setTitle("删除文件夹");
        // alert.setHeaderText("当前操作只会在该列表删除文件，并不会在硬盘上删除文件");
        // // 设置对话框的 icon 图标，参数是主窗口的 stage
        // alert.initOwner(importStage);
        // // showAndWait() 将在对话框消失以前不会执行之后的代码
        // Optional<ButtonType> buttonType = alert.showAndWait();
        // // 根据点击结果返回
        // if (buttonType.get().getButtonData().equals(ButtonData.YES)) {
        // item.getParent().getChildren().remove(item);
        // deleteFileFromTreeItem(item);
        // }
        // }
        // }
        // }
        // });
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

                List<FileImportRecord> recordList = new LinkedList<>();
                CheckBoxTreeItem<FileImportRecord> root =
                        (CheckBoxTreeItem<FileImportRecord>) treeTableView.getRoot();
                ObservableList<TreeItem<FileImportRecord>> children = root.getChildren();

                getFileImportRecodFromTreeItem(recordList, children);

                // for (int i = 0; i < recordList.size(); i++) {
                // System.out.println(
                // "========== " + (i + 1) + ": " + recordList.get(i).getFileName());
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

    private void getFileImportRecodFromTreeItem(List<FileImportRecord> recordList,
            ObservableList<TreeItem<FileImportRecord>> children) {

        for (int i = 0; i < children.size(); i++) {
            CheckBoxTreeItem<FileImportRecord> childItem =
                    (CheckBoxTreeItem<FileImportRecord>) children.get(i);
            // 如果是全选了 那么 叶子节点，全部保存
            // if(childItem.isSelected()) {
            // String fName = childItem.getValue().getFileName();
            // boolean isLeaf = childItem.isLeaf();
            // System.out.println("======================= checked "+fName+ ", isLeaf:"+isLeaf);
            // } else if(childItem.isIndeterminate()) {
            // // 如果没有全选，但是有部分子节点 勾选了
            //// 那么 查找勾选的子节点
            // String fName = childItem.getValue().getFileName();
            // boolean isLeaf = childItem.isLeaf();
            // System.out.println("======================= Indeterminate checked "+fName+ ",
            // isLeaf:"+isLeaf);
            // }
            if (childItem.isSelected() || childItem.isIndeterminate()) {
                // 如果是 叶子节点，那么肯定是选中了
                if (childItem.isLeaf()) {
                    recordList.add(childItem.getValue());
                } else {
                    // 如果是文件夹，那么需要递归
                    ObservableList<TreeItem<FileImportRecord>> children2 = childItem.getChildren();
                    getFileImportRecodFromTreeItem(recordList, children2);
                }
            } else {
                continue;
            }
        }
    }

    // private void deleteFileFromTreeItem(TreeItem<FileImportRecord> item) {
    // ObservableList<TreeItem<FileImportRecord>> children = item.getChildren();
    // for (int z = 0; z < children.size(); z++) {
    // TreeItem<FileImportRecord> node = children.get(z);
    // if (node.getValue().isFile()) {
    // for (int i = 0; i < recordList.size(); i++) {
    // System.out.println("===== delete file path:" + node.getValue().getFilePath());
    // if (recordList.get(i).getFilePath().equals(node.getValue().getFilePath())) {
    // recordList.remove(i);
    // break;
    // }
    // }
    // } else {
    // deleteFileFromTreeItem(node);
    // }
    // }
    // }

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
                // System.out.println(partitons.get(j).getMountPoint());
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
                // System.out.println("============ folderPath:" + folderPath);
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
                mountPoint = vlm.getMountPoint() + "\\";
                // System.out.println("==== hdID:" + hdID);
                // System.out.println("======= ptID:" + ptID);
                // System.out.println("========== hdUniqueCode:" + hdUniqueCode);
                // System.out.println("============== hdNickname:" + hdNickname);
                // System.out.println("================= ptUUID:" + ptUUID);
                // System.out.println("==================== mountPoint:" + mountPoint);
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
            CheckBoxTreeItem<FileImportRecord> itemRoot =
                    new CheckBoxTreeItem<FileImportRecord>(rootFI);
            itemRoot.setExpanded(true);

            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    if (files[i].getName().equals(".DS_Store")
                            || files[i].getName().equals("._.DS_Store")
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
                    // recordList.add(fi);
                    CheckBoxTreeItem<FileImportRecord> item =
                            new CheckBoxTreeItem<FileImportRecord>(fi, new ImageView(fileIcon));
                    item.setSelected(true);
                    itemRoot.getChildren().add(item);
                } else {
                    CheckBoxTreeItem<FileImportRecord> folderItem = loadMoreFiles(files[i],
                            hdUniqueCode, hdNickname, hdID, ptID, ptUUID, mountPoint, platform);
                    folderItem.setSelected(true);
                    itemRoot.getChildren().add(folderItem);
                }
            }
            treeTableFileImport.setRoot(itemRoot);
            treeTableFileImport.setShowRoot(false);
        }
    }

    private CheckBoxTreeItem<FileImportRecord> loadMoreFiles(File folder, String hdUniqueCode,
            String hdNickname, Long hdID, Long ptID, String ptUUID, String mountPoint,
            String platform) {
        FileImportRecord folderRecord = new FileImportRecord(folder);
        CheckBoxTreeItem<FileImportRecord> parentfolderItem =
                new CheckBoxTreeItem<FileImportRecord>(folderRecord, new ImageView(folderIcon));
        parentfolderItem.setSelected(true);
        File[] files = folder.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                if (files[i].getName().equals(".DS_Store")
                        || files[i].getName().equals("._.DS_Store")
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
                // recordList.add(fi);
                CheckBoxTreeItem<FileImportRecord> fileItem =
                        new CheckBoxTreeItem<FileImportRecord>(fi, new ImageView(fileIcon));
                fileItem.setSelected(true);
                parentfolderItem.getChildren().add(fileItem);
            } else {
                CheckBoxTreeItem<FileImportRecord> folderItem = loadMoreFiles(files[i],
                        hdUniqueCode, hdNickname, hdID, ptID, ptUUID, mountPoint, platform);
                // folderItem.setGraphic(folderIcon);
                folderItem.setSelected(true);
                parentfolderItem.getChildren().add(folderItem);
            }
        }
        return parentfolderItem;
    }
}
