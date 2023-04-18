package bio.harshana.controllers;

import bio.harshana.bo.BOFactory;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import bio.harshana.bo.custom.UserBO;
import bio.harshana.dto.UserDTO;

import java.util.List;

public class SettingsFormController {

    public TextField txtUsername;
    public PasswordField txtCurrentPass;
    public PasswordField txtNewPass;
    public JFXToggleButton toggleNewPass;
    public JFXToggleButton toggleCurrentPass;
    UserBO user = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);
    private UserDTO userDTO;
    private String password = "";

    public void initialize() {
        List<UserDTO> all = user.getAll();

        userDTO = all.get(0);
        txtUsername.setText(userDTO.getUsername());
        password = all.get(0).getPassword();
    }

    public void saveOnAction(ActionEvent actionEvent) {
        if (txtCurrentPass.getText().equals(password)) {
            userDTO.setPassword(txtNewPass.getText());
            user.update(userDTO);
            new Alert(Alert.AlertType.INFORMATION, "Done!").show();


        } else {
            new Alert(Alert.AlertType.ERROR, "Incorrect password").show();
        }
    }

    public void toggleNew() {
        if (toggleNewPass.isSelected()) {
            txtNewPass.setPromptText(txtNewPass.getText());
            txtNewPass.setText("");
            txtNewPass.setDisable(true);
        } else {
            txtNewPass.setText(txtNewPass.getPromptText());
            txtNewPass.setPromptText("Password");
            txtNewPass.setDisable(false);
        }
    }

    public void toggleCurrent() {
        if (toggleCurrentPass.isSelected()) {
            txtCurrentPass.setPromptText(txtCurrentPass.getText());
            txtCurrentPass.setText("");
            txtCurrentPass.setDisable(true);
        } else {
            txtCurrentPass.setText(txtNewPass.getPromptText());
            txtCurrentPass.setPromptText("Password");
            txtCurrentPass.setDisable(false);
        }
    }
}