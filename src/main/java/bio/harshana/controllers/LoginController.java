package bio.harshana.controllers;

import bio.harshana.bo.BOFactory;
import com.jfoenix.controls.JFXToggleButton;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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

    public void loginOnAction() {
        UserBO user = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);
        List<UserDTO> all = user.getAll();

        for (UserDTO dto : all) {
            if (txtEmail.getText().equals(dto.getUsername()) && txtPassword.getText().equals(dto.getPassword())) {
                try {
                    Navigation.navigation(Routes.DASHBOARD, AnchorPaneContext);
                } catch (IOException e) {
                    new Alert(Alert.AlertType.ERROR, "Error Loading Dashboard").show();
                }
            }
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
}
