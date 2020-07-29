package org.tools.fx.library.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.EntityManager;
import org.tools.fx.library.App;
import org.tools.fx.library.LoadVolumes;
import org.tools.fx.library.db.DBHelper;
import org.tools.fx.library.entity.FileRecord;
import org.tools.fx.library.enums.PlatformEnum;
import org.tools.fx.library.model.Volume;
import org.tools.fx.library.tools.FormatHelper;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class MainWindowController implements Initializable {


    @FXML
    private Button btnRefresh;

    @FXML
    CheckBox ckAllLib;
    @FXML
    ComboBox<String> cmbMainType;
    @FXML
    TextField txtSearch;

    @FXML
    Button btnSaveFile;
    @FXML
    private Button btnSearch;
    @FXML
    private TreeView<Volume> treeVolume;

    @FXML
    private TableView<FileRecord> fileSearchRecord;

    @FXML
    private TableColumn<FileRecord, Number> colNO;

    // @FXML
    // private TableColumn<FileRecord, Number> fileId;
    // @FXML
    // private TableColumn<FileRecord, Number> hdID;
    // @FXML
    // private TableColumn<FileRecord, Number> ptID;
    // @FXML
    // private TableColumn<FileRecord, String> hdName;

    // 硬盘别名
    @FXML
    private TableColumn<FileRecord, String> hdNickname;
    /**
     * Win序列号
     */
    @FXML
    private TableColumn<FileRecord, String> hdWinSerialNumber;
    // mac序列号
    @FXML
    private TableColumn<FileRecord, String> hdMacSerialNumber;
    @FXML
    private TableColumn<FileRecord, String> winUUID;
    @FXML
    private TableColumn<FileRecord, String> macUUID;
    // 文件名称
    @FXML
    private TableColumn<FileRecord, String> fileName;
    // 文件别名
    @FXML
    private TableColumn<FileRecord, String> subtitle;
    @FXML
    private TableColumn<FileRecord, String> filePath;
    // 挂载点
    @FXML
    private TableColumn<FileRecord, String> mountPoint;
    @FXML
    private TableColumn<FileRecord, String> fileSize;
    @FXML
    private TableColumn<FileRecord, String> fileMainType;
    @FXML
    private TableColumn<FileRecord, String> fileMark;
    @FXML
    private TableColumn<FileRecord, String> fileTip;
    @FXML
    private TableColumn<FileRecord, String> fileSuffix;
    @FXML
    private TableColumn<FileRecord, String> lastModified;

    @FXML
    private Pagination pageFileSearchRecord;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // System.out.println("============= MainWindowController location:" + location.getFile());
        // if (resources == null) {
        // System.out.println("============== resources == null");
        // } else {
        // Enumeration<String> keys = resources.getKeys();
        // while (keys.hasMoreElements()) {
        // String key = keys.nextElement();
        // System.out.println(" =========== key:" + key + ", value:" + resources.getObject(key));
        // }
        // }
        fileSearchRecord.setEditable(true);
        ObservableList<String> items =
                FXCollections.observableArrayList("", "文档", "视频", "音乐", "图片", "软件");
        cmbMainType.setItems(items);

        colNO.setCellFactory(
                new Callback<TableColumn<FileRecord, Number>, TableCell<FileRecord, Number>>() {
                    @Override
                    public TableCell<FileRecord, Number> call(
                            TableColumn<FileRecord, Number> param) {
                        return new TableCell<FileRecord, Number>() {
                            @Override
                            protected void updateItem(Number item, boolean empty) {
                                // super.updateItem(item, empty);
                                this.setText(null);
                                this.setGraphic(null);
                                if (!empty) {
                                    this.setText(String.valueOf(this.getIndex() + 1));
                                }
                            }
                        };
                    }
                });
        // fileId.setCellValueFactory(new PropertyValueFactory<FileRecord, Number>("fileId"));
        // hdID.setCellValueFactory(new PropertyValueFactory<FileRecord, Number>("hdID"));
        // ptID.setCellValueFactory(new PropertyValueFactory<FileRecord, Number>("ptID"));
        // hdName.setCellValueFactory(new PropertyValueFactory<FileRecord, String>("hdName"));
        hdNickname.setCellValueFactory(new PropertyValueFactory<FileRecord, String>("hdNickname"));
        hdWinSerialNumber.setCellValueFactory(
                new PropertyValueFactory<FileRecord, String>("hdWinSerialNumber"));
        hdMacSerialNumber.setCellValueFactory(
                new PropertyValueFactory<FileRecord, String>("hdMacSerialNumber"));
        winUUID.setCellValueFactory(new PropertyValueFactory<FileRecord, String>("winUUID"));
        macUUID.setCellValueFactory(new PropertyValueFactory<FileRecord, String>("macUUID"));
        fileName.setCellValueFactory(new PropertyValueFactory<FileRecord, String>("fileName"));
        subtitle.setCellValueFactory(new PropertyValueFactory<FileRecord, String>("subtitle"));
        filePath.setCellValueFactory(new PropertyValueFactory<FileRecord, String>("filePath"));
        mountPoint.setCellValueFactory(new PropertyValueFactory<FileRecord, String>("mountPoint"));
        // fileSize.setCellValueFactory(new PropertyValueFactory<FileRecord, String>("fileSize"));
        fileMainType
                .setCellValueFactory(new PropertyValueFactory<FileRecord, String>("fileMainType"));
        fileMark.setCellValueFactory(new PropertyValueFactory<FileRecord, String>("fileMark"));
        fileTip.setCellValueFactory(new PropertyValueFactory<FileRecord, String>("fileTip"));
        fileSuffix.setCellValueFactory(new PropertyValueFactory<FileRecord, String>("fileSuffix"));
        // lastModified;
        fileSize.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<FileRecord, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(
                            CellDataFeatures<FileRecord, String> param) {
                        return new SimpleStringProperty(
                                FormatHelper.format(param.getValue().getFileSize()));
                    }
                });

        lastModified.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<FileRecord, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(
                            CellDataFeatures<FileRecord, String> param) {
                        return new SimpleStringProperty(
                                FormatHelper.formatDate(param.getValue().getLastModified()));
                    }
                });
        fileMainType.setCellFactory(ComboBoxTableCell.forTableColumn("文档", "视频", "音乐", "图片", "软件"));
        fileMainType.setOnEditCommit(new EventHandler<CellEditEvent<FileRecord, String>>() {
            @Override
            public void handle(CellEditEvent<FileRecord, String> event) {
                event.getRowValue().setFileMainType(event.getNewValue());
            }
        });

        fileMark.setCellFactory(ComboBoxTableCell.forTableColumn("学习", "工作", "爱好"));
        fileMark.setOnEditCommit(new EventHandler<CellEditEvent<FileRecord, String>>() {
            @Override
            public void handle(CellEditEvent<FileRecord, String> event) {
                event.getRowValue().setFileMark(event.getNewValue());
            }
        });
        fileTip.setCellFactory(TextFieldTableCell.forTableColumn());
        fileTip.setOnEditCommit(new EventHandler<CellEditEvent<FileRecord, String>>() {
            @Override
            public void handle(CellEditEvent<FileRecord, String> event) {
                event.getRowValue().setFileTip(event.getNewValue());
            }
        });


        subtitle.setCellFactory(
                new Callback<TableColumn<FileRecord, String>, TableCell<FileRecord, String>>() {
                    @Override
                    public TableCell<FileRecord, String> call(
                            TableColumn<FileRecord, String> param) {
                        return new TextFieldTableCell<FileRecord, String>();
                    }
                });
        subtitle.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<FileRecord, String>>() {
            @Override
            public void handle(CellEditEvent<FileRecord, String> event) {
                event.getRowValue().setSubtitle(event.getNewValue());
            }
        });
        mountPoint.setCellFactory(
                new Callback<TableColumn<FileRecord, String>, TableCell<FileRecord, String>>() {
                    @Override
                    public TableCell<FileRecord, String> call(
                            TableColumn<FileRecord, String> param) {
                        return new TableCell<FileRecord, String>() {

                            @Override
                            protected void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item == null || item.isEmpty()) {

                                } else {
                                    // getTableRow().getItem();
                                    this.setStyle("-fx-font-weight: bold;-fx-font-size: 16;");
                                    this.setTextFill(Color.rgb(34, 140, 34));
                                    setText(item);
                                }
                            }
                        };
                    }
                });

        // fileSearchRecord.setRowFactory(new Callback<TableView<FileRecord>,
        // TableRow<FileRecord>>() {
        // @Override
        // public TableRow<FileRecord> call(TableView<FileRecord> param) {
        // return new TableRow<FileRecord>() {
        //
        // @Override
        // protected void updateItem(FileRecord item, boolean empty) {
        // super.updateItem(item, empty);
        // if (!empty) {
        // this.setStyle("-fx-font-weight: bold");
        //// styleProperty().bind(Bindings.when(selectedProperty())
        //// .then("-fx-font-weight: bold; -fx-font-size: 18;")
        //// .otherwise(""));
        // }
        // }
        //
        // };
        // }
        // });
        // fileSearchRecord.getItems().setAll(parseUserList());

        // pageFileSearchRecord.setPageFactory(new Callback<Integer, Node>() {
        // @Override
        // public Node call(Integer param) { // 页码
        // System.out.println("================= param:" + param);
        // loadFileSearRecord(param);
        // return fileSearchRecord;
        // }
        // });

        pageFileSearchRecord.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue,
                    Number newValue) {
                System.out.println("=========== page listener:" + newValue.intValue());
                loadFileSearRecord(newValue.intValue());
            }
        });

        // treeVolume.getSelectionModel().selectedItemProperty()
        // .addListener(new ChangeListener<TreeItem<Volume>>() {
        // @Override
        // public void changed(ObservableValue<? extends TreeItem<Volume>> observable,
        // TreeItem<Volume> oldValue, TreeItem<Volume> newValue) {
        // Volume volume = newValue.getValue();
        // System.out.println("========== volume:" + volume);
        // }
        // });

        // 硬盘信息 挂载
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                App.refreshDisks();
                TreeItem<Volume> rootItem = LoadVolumes.getHelper().loadVolumes();
                treeVolume.setRoot(rootItem);
                treeVolume.setShowRoot(false);
            }
        });

    }


    public void onBtnRefreshClick(ActionEvent event) {
        App.refreshDisks();
        TreeItem<Volume> rootItem = LoadVolumes.getHelper().loadVolumes();
        treeVolume.setRoot(rootItem);
        treeVolume.setShowRoot(false);

    }

    /**
     * 打开文件
     * 
     * @param event
     */
    public void onBtnOpenFileClick(ActionEvent event) {
        FileRecord fileRecord = fileSearchRecord.getSelectionModel().getSelectedItem();
        if (fileRecord == null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("信息");
            alert.setHeaderText("您好像没有选择文件");
            // alert.setContentText("您好像没有选择文件");
            alert.show();
        } else {
            if (fileRecord.getMountPoint() == null || fileRecord.getMountPoint().isEmpty()) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("信息");
                alert.setHeaderText("无法打开文件");
                alert.setContentText("文件所在的硬盘，好像没有接入电脑");
                alert.show();
            } else {
                File f = new File(fileRecord.getMountPoint() + fileRecord.getFilePath());
                if (!f.exists()) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("信息");
                    alert.setHeaderText("无法打开文件");
                    alert.setContentText("该文件不存在");
                    alert.show();
                }
                try {
                    Desktop.getDesktop().open(f.getParentFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void onBtnSaveFileClick(ActionEvent event) {
        // boolean allLib = ckAllLib.isSelected();
        // String fileMainType = cmbMainType.getValue();
        // String keyword = txtSearch.getText();
        //
        // System.out.println("======== allLib:" + allLib);
        // System.out.println("======== fileMainType:" + fileMainType);
        // System.out.println("======== keyword:" + keyword);
    }

    public void onBtnDeleteFileClick(ActionEvent event) {
        // boolean allLib = ckAllLib.isSelected();
        // String fileMainType = cmbMainType.getValue();
        // String keyword = txtSearch.getText();
        //
        // System.out.println("======== allLib:" + allLib);
        // System.out.println("======== fileMainType:" + fileMainType);
        // System.out.println("======== keyword:" + keyword);
    }

    public void onBtnSearchClick(ActionEvent event) {
        loadFileSearRecord(0);

    }


    /**
     * 到数据库中查询FileSearRecord
     * 
     * @param pageNum
     */
    private void loadFileSearRecord(int pageNum) {
        // System.out.println("======== pageNum:" + pageNum);

        boolean allLib = ckAllLib.isSelected();
        String mainType = cmbMainType.getValue();
        String keyword = txtSearch.getText();

        // System.out.println("======== allLib:" + allLib);
        // System.out.println("======== fileMainType:" + mainType);
        // System.out.println("======== keyword:" + keyword);

        String countSQL = " select count(f) from FileRecord f  where 1=1  ";
        String SQL = " select f from FileRecord f  where 1=1 ";
        if (!allLib) {
            TreeItem<Volume> selectItem = treeVolume.getSelectionModel().getSelectedItem();
            if (selectItem != null) {
                Volume vlm = selectItem.getValue();
                if (vlm.getvType() == 1) {
                    countSQL += " and f.hdID= " + vlm.getHdID();
                    SQL += " and f.hdID= " + vlm.getHdID();
                } else {
                    countSQL += " and f.ptID= " + vlm.getPtID();
                    SQL += " and f.ptID= " + vlm.getPtID();
                }
            }
        }

        if (mainType != null && !mainType.isEmpty()) {
            countSQL += " and f.fileMainType= '" + mainType + "'";
            SQL += " and f.fileMainType= '" + mainType + "'";
        }
        if (keyword != null && !keyword.isEmpty()) {
            countSQL += " and (  f.fileName like '%" + keyword + "%' or  f.subtitle like '%"
                    + keyword + "%'  or  f.filePath like  '%" + keyword + "%' or f.fileTip like '%"
                    + keyword + "%' ) ";
            SQL += " and (  f.fileName like '%" + keyword + "%' or  f.subtitle like '%" + keyword
                    + "%'  or  f.filePath like  '%" + keyword + "%' or f.fileTip like '%" + keyword
                    + "%' )";
        }
        EntityManager em = DBHelper.getEM();
        Long total = em.createQuery(countSQL, Long.class).getSingleResult();
        List<FileRecord> data = em.createQuery(SQL, FileRecord.class).setFirstResult(pageNum * 25)
                .setMaxResults(25).getResultList();

        // System.out.println("===============data.size(): " + data.size());
        if (data == null || data.size() == 0) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("信息");
            alert.setHeaderText("没有查询到结果");
            // alert.setContentText("请删除文件夹下的文件!");
            alert.showAndWait();
        } else {
            pageFileSearchRecord.setPageCount(total.intValue() / 25 + 1);

            // windows 外接的硬盘 挂载点 可能会跟上次不一样，
            // 另外，如果文件在外接硬盘上，但是本次硬盘没有接入电脑，那么该文件显示 inactive
            if (App.os == PlatformEnum.WINDOWS) {
                for (int i = 0; i < data.size(); i++) {
                    FileRecord fr = data.get(i);
                    String currentMountPoint =
                            findCurrentMountPointFromCurrentVolumes(fr.getWinUUID());
                    fr.setMountPoint(currentMountPoint);
                }
            } else {
                for (int i = 0; i < data.size(); i++) {
                    FileRecord fr = data.get(i);
                    String currentMountPoint =
                            findCurrentMountPointFromCurrentVolumes(fr.getMacUUID());
                    fr.setMountPoint(currentMountPoint);
                }
            }

            ObservableList<FileRecord> list = FXCollections.observableArrayList(data);
            fileSearchRecord.setItems(list);
        }
    }


    private String findCurrentMountPointFromCurrentVolumes(String UUID) {
        for (int i = 0; i < App.currentVolumes.size(); i++) {
            List<Volume> volumes = App.currentVolumes.get(i).getVolumes();
            for (int j = 0; j < volumes.size(); j++) {
                Volume vlm = volumes.get(j);
                if (vlm.getUuid().equals(UUID)) {
                    // if (UUID.equals(vlm.getUuid())) {
                    return vlm.getMountPoint();
                }
            }
        }
        return "";
    }



}
