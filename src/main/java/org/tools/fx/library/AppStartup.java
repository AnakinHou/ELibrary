package org.tools.fx.library;

import java.net.URL;
import javax.swing.ImageIcon;
import org.tools.fx.library.db.DBHelper;
import org.tools.fx.library.model.Result;
import org.tools.fx.library.service.UserService;
import org.tools.fx.library.tools.Encode;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AppStartup extends Application {

    // private static final Logger log = LoggerFactory.getLogger(AppStartup.class);

    private static final int LENGTH = 5;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        HBox rootNode = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        Scene scene = new Scene(rootNode);
        stage.setResizable(false);
        stage.setTitle("Elibrary");
        stage.setScene(scene);

        stage.getIcons().add(new Image("/images/logo.png"));
        // stage.getIcons().add(new Image(<yourclassname>.class.getResourceAsStream("icon.png")));
        // Image image = new Image("/images/logo.png");
        // com.apple.eawt.Application.getApplication().setDockIconBadge(image);
        // com.apple.eawt.Application.getApplication().setDockIconImage(new
        // ImageIcon("/images/logo.png").getImage());
        try {
            URL iconURL = AppStartup.class.getResource("/images/logo.png");
            java.awt.Image image = new ImageIcon(iconURL).getImage();
            com.apple.eawt.Application.getApplication().setDockIconImage(image);
        } catch (Exception e) {
            // Won't work on Windows or Linux.
            e.printStackTrace();
        }


        stage.show();


        TextField txtUsername = (TextField) scene.lookup("#txt_username");
        PasswordField txtPassword = (PasswordField) scene.lookup("#txt_password");

        Button loginBtn = (Button) rootNode.lookup("#btn_login");
        Label lblMsg = (Label) rootNode.lookup("#lbl_msg");
        txtUsername.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                // txtUsername
                if (newValue.trim().length() < LENGTH
                        || txtPassword.getText().trim().length() < LENGTH) {
                    loginBtn.setDisable(true);
                } else {
                    loginBtn.setDisable(false);
                }
            }
        });

        txtPassword.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                // txtUsername
                if (newValue.trim().length() < LENGTH
                        || txtUsername.getText().trim().length() < LENGTH) {
                    loginBtn.setDisable(true);
                } else {
                    loginBtn.setDisable(false);
                }
            }
        });


        loginBtn.setOnAction((ActionEvent e) -> {
            // System.out.println("clicked");
            String uname = txtUsername.getText();
            String upass = txtPassword.getText();
            // System.out.println("uname:" + uname);
            // System.out.println("upass:" + upass);
            if (uname == null || uname.length() < LENGTH) {
                lblMsg.setText("username length short that 6");
                return;
            }
            if (upass == null || upass.length() < LENGTH) {
                lblMsg.setText("upass length short that 6");
                return;
            }
            // got to login
            Result result = gotoLogin(uname, upass);
            if (result.isSuccess()) {
                // lblMsg.setText(result.getMessage());
                MainWindow mainwin = new MainWindow();
                Stage mainStage = new Stage();
                try {
                    mainwin.start(mainStage);
                    stage.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            } else {
                lblMsg.setText(result.getMessage());
            }
        });

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                // System.out.print("监听到窗口关闭");
                stage.close();
                Platform.exit();
                System.exit(0);
                // try {
                // stop();
                // } catch (Exception e) {
                // e.printStackTrace();
                // }
            }
        });

        // DBHelper.initDB();
        new Thread(new Runnable() {
            @Override
            public void run() {
                DBHelper.initDB();
            }
        }).start();
    }

    private Result gotoLogin(String username, String password) {
        String unameEncode = Encode.getSHA256Str(username.trim());
        String upassEncode = Encode.getSHA256Str(password.trim());
        String allEncode = Encode.getSHA256Str(username.trim() + password.trim());
        UserService uService = new UserService();
        return uService.login(unameEncode, upassEncode, allEncode);
        // return new Result(false, "登录失败");
    }

}
