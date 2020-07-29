package org.tools.fx.library;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainWindow extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox rootNode = FXMLLoader.load(getClass().getResource("/fxml/MainView.fxml"));
        Scene scene = new Scene(rootNode);
//        primaryStage.setResizable(false);
        primaryStage.setTitle("Elibrary");
        primaryStage.setScene(scene);
        primaryStage.show();


        // 刷新硬盘
//        Button btnRefresh = (Button) rootNode.lookup("#btn_refresh");
        
        // 硬盘管理
        Button btnHdManage = (Button) rootNode.lookup("#btn_hd_manage");
        
        // 导入资料
        Button btnImport = (Button) rootNode.lookup("#btn_import");
        
        // 查询
//        Button btnSearch = (Button) rootNode.lookup("#btn_search");

        // ObservableList<FileRecord> data = FXCollections.observableArrayList();
        // TableView<FileRecord> fileTable = (TableView<FileRecord>)
        // rootNode.lookup("#file_record_table");
        //
        //
        // fileTable.setItems(data);
        //
        // fileTable.getColumns().get(0).setCellValueFactory( new Callback<FileRecord, String>() {
        //
        // @Override
        // public ObservableValue<Object> call(CellDataFeatures<FileRecord, String> param) {
        // // TODO Auto-generated method stub
        // return null;
        // }
        //
        // });
 
     
        btnHdManage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // event.getSource()
                new HardDriveManageWindow(primaryStage);
            }
        });


        btnImport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new ImportDocWindow(primaryStage);
            }
        });


        // primaryStage.add.ac.setOnCloseRequest(e -> {
        // primaryStage.close();
        // Platform.exit();
        // });
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                // System.out.print("监听到窗口关闭");
                primaryStage.close();
                Platform.exit();
                System.exit(0);
                // try {
                // stop();
                // } catch (Exception e) {
                // e.printStackTrace();
                // }
            }
        });

    }

}
