package bio.harshana.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import bio.harshana.bo.BOFactory;
import bio.harshana.bo.custom.UserBO;
import bio.harshana.dto.UserDTO;
import bio.harshana.util.Navigation;
import bio.harshana.util.Routes;

import java.io.IOException;
import java.util.List;

public class LoginController {
    public AnchorPane AnchorPaneContext;
    public TextField txtEmail;
    public PasswordField txtPassword;
    public Label passwordStatus;
    public JFXToggleButton passwordShow;
    public JFXButton btnClose;

    public void loginOnAction() {
        try {
            UserBO user = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);
            List<UserDTO> all = user.getAll();

            for (UserDTO dto : all) {
                if (txtEmail.getText().equals(dto.getUsername()) && txtPassword.getText().equals(dto.getPassword())) {

                    LoginSessions.user = dto;
                    try {
                        Navigation.navigation(Routes.DASHBOARD, AnchorPaneContext);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    passwordStatus.setText("Incorrect Username or Password");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void login() {
        loginOnAction();
    }

    public void togglePassword() {
        if (passwordShow.isSelected()) {
            txtPassword.setPromptText(txtPassword.getText());
            txtPassword.setText("");
            txtPassword.setDisable(true);
            passwordShow.setText("Hide Password");
        } else {
            txtPassword.setText(txtPassword.getPromptText());
            txtPassword.setPromptText("Password");
            passwordShow.setText("Show Password");
            txtPassword.setDisable(false);
        }
    }

    public void closeOnAction(ActionEvent actionEvent) {
        Platform.exit();
    }
}
