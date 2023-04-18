package bio.harshana.controllers;

import bio.harshana.bo.BOFactory;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import bio.harshana.bo.custom.StudentBO;
import bio.harshana.dto.StudentDTO;
import bio.harshana.tm.StudentTM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentFormController {

    private final StudentBO studentBO = (StudentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STUDENT);
    public TextField txtID;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtContactNO;
    public JFXButton btnAdd;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public TableView<StudentTM> tblStudent;
    public TableColumn<StudentTM, String> colID;
    public TableColumn<StudentTM, String> colName;
    public TableColumn<StudentTM, String> colGender;
    public TableColumn<StudentTM, LocalDate> colDOB;
    public TableColumn<StudentTM, String> colAddress;
    public TableColumn<StudentTM, String> colContactNO;
    public ComboBox<String> cmbGender;
    public DatePicker dobPicker;

    public void initialize() {
        tblStudent.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblStudent.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblStudent.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("gender"));
        tblStudent.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("dob"));
        tblStudent.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblStudent.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("address"));

        loadAllStudents();

        tblStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(false);
            btnAdd.setDisable(true);
            btnUpdate.setDisable(false);

            if (newValue != null) {
                txtID.setText(newValue.getId());
                txtID.setEditable(false);
                txtName.setText(newValue.getName());
                txtAddress.setText((newValue.getAddress()));
                dobPicker.setValue((newValue.getDob()));
                cmbGender.setValue(newValue.getGender());
                txtContactNO.setText(newValue.getContact());

            }
        });

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        List<String> list = new ArrayList<>();
        list.add("Male");
        list.add("Female");
        ObservableList<String> obList = FXCollections.observableList(list);
        cmbGender.setItems(obList);
    }

    private void loadAllStudents() {
        tblStudent.getItems().clear();

        List<StudentDTO> allStudents = studentBO.getAll();
        if (allStudents != null) {
            for (StudentDTO student : allStudents) {
                System.out.println("Loop");
                System.out.println(student.getId());
                tblStudent.getItems().add(new StudentTM(student.getId(), student.getName(), student.getAddress(), student.getContact(), student.getDob(), student.getGender()));
            }
        }

    }

    public void updateOnAction() {
        boolean saved = studentBO.update(new StudentDTO(
                txtID.getText(),
                txtName.getText(),
                txtAddress.getText(),
                txtContactNO.getText(),
                dobPicker.getValue(),
                cmbGender.getValue()
        ));
        if (saved) {
            loadAllStudents();
            clear();
            new Alert(Alert.AlertType.CONFIRMATION, "Done Saving").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Error Saving").show();
        }
    }

    public void deleteOnAction() {
        boolean saved = studentBO.delete(new StudentDTO(
                txtID.getText(),
                txtName.getText(),
                txtAddress.getText(),
                txtContactNO.getText(),
                dobPicker.getValue(),
                cmbGender.getValue()
        ));
        if (saved) {
            loadAllStudents();
            clear();
            new Alert(Alert.AlertType.CONFIRMATION, "Done Deleting").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Error Deleting").show();
        }
    }

    public void saveOnAction(ActionEvent actionEvent) {
        if (studentBO.isExists(txtID.getText())) {
            new Alert(Alert.AlertType.WARNING, "Already Saved Room").show();
            return;
        }
        boolean saved = studentBO.save(new StudentDTO(
                        txtID.getText(),
                        txtName.getText(),
                        txtAddress.getText(),
                        txtContactNO.getText(),
                        dobPicker.getValue(),
                        cmbGender.getValue()
                )
        );
        if (saved) {
            setStudentsTable();
            clear();
            new Alert(Alert.AlertType.CONFIRMATION, "Done Saving").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Error Saving").show();
        }
    }

    private void setStudentsTable() {
        tblStudent.getItems().clear();
        List<StudentDTO> allRooms = studentBO.getAll();
        if (allRooms != null) {
            for (StudentDTO room : allRooms) {
                tblStudent.getItems().add(new StudentTM(room.getId(), room.getName(), room.getAddress(), room.getContact(), room.getDob(), room.getGender()));
            }
        }
    }

    private void clear() {
        txtAddress.clear();
        txtID.clear();
        txtName.clear();
        txtContactNO.clear();
        cmbGender.getSelectionModel().clearSelection();

    }
}
