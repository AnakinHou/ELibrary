package org.tools.fx.library;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.tools.fx.library.db.DBHelper;
import org.tools.fx.library.entity.HardDrive;
import org.tools.fx.library.entity.Partition;
import org.tools.fx.library.enums.PlatformEnum;
import org.tools.fx.library.model.Result;
import org.tools.fx.library.model.Volume;
import org.tools.fx.library.service.HardDriveService;
import org.tools.fx.library.tools.FormatHelper;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableColumn.CellEditEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * 硬盘管理
 * 
 * @author hydra
 *
 */
public class HardDriveManageWindow {

    public HardDriveManageWindow(Stage parentStage) {

        Stage hdStage = new Stage();
        hdStage.initOwner(parentStage);
        // 阻止事件传递到任何其他应用程序窗口
        hdStage.initModality(Modality.APPLICATION_MODAL);

        hdStage.getIcons().add(new Image("/images/logo.png"));
        
        Button btnRefresh = new Button("刷新");
        Button btnSave = new Button("保存");
        btnSave.setDefaultButton(true);

        ToolBar toolbar = new ToolBar(btnRefresh, btnSave);
        toolbar.setPrefHeight(30d);

        // 硬盘 treetable
        TreeTableView<Volume> hdTreeTable = new TreeTableView<Volume>();
        hdTreeTable.setPrefHeight(370d);
        hdTreeTable.setPrefWidth(1000d);
        hdTreeTable.setEditable(true);

        TreeTableColumn<Volume, Long> colHDID = new TreeTableColumn<>("硬盘ID");
        colHDID.setPrefWidth(100d);

        TreeTableColumn<Volume, String> colName = new TreeTableColumn<>("名称");
        colName.setPrefWidth(120d);

        TreeTableColumn<Volume, String> colNickName = new TreeTableColumn<>("别名");
        colNickName.setPrefWidth(120d);

        TreeTableColumn<Volume, String> colModel = new TreeTableColumn<>("硬盘Model");
        colModel.setPrefWidth(140d);

        TreeTableColumn<Volume, Long> colPTID = new TreeTableColumn<>("分区ID");
        colPTID.setPrefWidth(100d);

        TreeTableColumn<Volume, String> colHdUniqueCode = new TreeTableColumn<>("硬盘序列号");
        colHdUniqueCode.setPrefWidth(260d);

        TreeTableColumn<Volume, String> colUUID = new TreeTableColumn<>("分区序列号");
        colUUID.setPrefWidth(260d);

        TreeTableColumn<Volume, String> colMountPoint = new TreeTableColumn<>("当前挂载点");
        colMountPoint.setPrefWidth(160d);

        TreeTableColumn<Volume, String> colSize = new TreeTableColumn<>("容量");
        colSize.setPrefWidth(120d);

        hdTreeTable.getColumns().addAll(colHDID, colName, colNickName, colModel, colPTID,
                colHdUniqueCode, colUUID, colMountPoint, colSize);

        colHDID.setCellValueFactory(new TreeItemPropertyValueFactory<Volume, Long>("hdID"));
        colName.setCellValueFactory(new TreeItemPropertyValueFactory<Volume, String>("name"));
        colNickName
                .setCellValueFactory(new TreeItemPropertyValueFactory<Volume, String>("nickname"));
        colModel.setCellValueFactory(new TreeItemPropertyValueFactory<Volume, String>("model"));
        colPTID.setCellValueFactory(new TreeItemPropertyValueFactory<Volume, Long>("ptID"));
        colHdUniqueCode.setCellValueFactory(
                new TreeItemPropertyValueFactory<Volume, String>("hdUniqueCode"));
        colUUID.setCellValueFactory(new TreeItemPropertyValueFactory<Volume, String>("uuid"));
        // colSize.setCellValueFactory(new TreeItemPropertyValueFactory<Volume, Long>("size"));
        // long gb = 1073741824l;
        colSize.setCellValueFactory(
                new Callback<TreeTableColumn.CellDataFeatures<Volume, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<Volume, String> param) {
                        return new SimpleStringProperty(
                                FormatHelper.format(param.getValue().getValue().getSize()));
                    }
                });

        colMountPoint.setCellValueFactory(
                new TreeItemPropertyValueFactory<Volume, String>("mountPoint"));

        colNickName.setCellFactory(
                new Callback<TreeTableColumn<Volume, String>, TreeTableCell<Volume, String>>() {
                    @Override
                    public TreeTableCell<Volume, String> call(
                            TreeTableColumn<Volume, String> param) {
                        return new TextFieldTreeTableCell<Volume, String>(
                                new StringConverter<String>() {
                                    @Override
                                    public String toString(String object) {
                                        return object;
                                    }

                                    @Override
                                    public String fromString(String string) {
                                        return string;
                                    }
                                });
                    }
                });

        colNickName
                .setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<Volume, String>>() {

                    @Override
                    public void handle(CellEditEvent<Volume, String> event) {
                        Volume volume = event.getRowValue().getValue();
                        String nickname = event.getNewValue();
                        volume.setNickname(nickname);

                    }
                });
        // colSize.setCellFactory(new Callback<TreeTableColumn<Volume,Long>,
        // TreeTableCell<Volume,Long>>() {
        //
        // @Override
        // public TreeTableCell<Volume, Long> call(TreeTableColumn<Volume, Long> param) {
        // // TODO Auto-generated method stub
        // return null;
        // }
        // });

        VBox vbox = new VBox(toolbar, hdTreeTable);
        vbox.setPrefHeight(600d);
        vbox.setPrefWidth(1024d);
        VBox.setMargin(hdTreeTable, new Insets(0, 0, 0, 0));
        VBox.setVgrow(hdTreeTable, Priority.ALWAYS);

        Scene scene = new Scene(vbox);
        hdStage.setScene(scene);

        hdStage.setTitle("硬盘管理");
        hdStage.show();

        hdTreeTable.setRoot(LoadVolumes.getHelper().loadVolumes());
        hdTreeTable.setShowRoot(false);

        btnRefresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                App.refreshDisks();
                TreeItem<Volume> rootItem = LoadVolumes.getHelper().loadVolumes();
                hdTreeTable.setRoot(rootItem);
                hdTreeTable.setShowRoot(false);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("信息");
                alert.setHeaderText("硬盘信息已经刷新");
                // alert.setContentText(" !");
                alert.show();
            }
        });

        // 保存
        btnSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<TreeItem<Volume>> list = hdTreeTable.getRoot().getChildren();
                List<Volume> volumeList = new ArrayList<Volume>();

                for (int i = 0; i < list.size(); i++) {
                    Volume hd = list.get(i).getValue();
                    System.out.println(hd.getName() + ", " + hd.getNickname());
                    for (int j = 0; j < hd.getVolumes().size(); j++) {
                        System.out.println("    |---" + hd.getVolumes().get(j).getName());
                    }
                    if (hd.getNickname() == null || hd.getNickname().isEmpty()
                            || hd.getModel() == null || hd.getModel().isEmpty()) {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("信息");
                        alert.setHeaderText("硬盘[" + hd.getName() + "] 没有起别名！");
                        // alert.setContentText(" !");
                        alert.show();
                        return;
                    }
                    volumeList.add(hd);
                }
                Result result = HardDriveService.getService().updateHardDrive(volumeList);
                // if(result.isSuccess()) {
                // }
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("信息");
                alert.setHeaderText(result.getMessage());
                // alert.setContentText(" !");
                alert.show();
            }
        });
    }



}
