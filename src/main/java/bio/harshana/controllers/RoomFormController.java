package bio.harshana.controllers;

import bio.harshana.bo.BOFactory;
import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import bio.harshana.bo.custom.RoomBO;
import bio.harshana.dto.RoomDTO;
import bio.harshana.tm.RoomTM;

import java.util.List;

public class RoomFormController {

    private final RoomBO roomBO = (RoomBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ROOM);
    public TextField txtID;
    public TextField txtType;
    public TextField txtKeyMoney;
    public TextField txtQty;
    public JFXButton btnAdd;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public TableColumn<RoomTM, String> colID;
    public TableColumn<RoomTM, String> colType;
    public TableColumn<RoomTM, Double> colKeyMoney;
    public TableColumn<RoomTM, Integer> colQTY;
    public TableView<RoomTM> tblRooms;


    public void initialize() {
        tblRooms.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblRooms.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("type"));
        tblRooms.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("keyMoney"));
        tblRooms.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("qty"));
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        setRoomsTable();
        txtID.setText("RM-");
        txtID.requestFocus();

        tblRooms.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(false);
            btnAdd.setDisable(true);
            btnUpdate.setDisable(false);

            if (newValue != null) {
                txtID.setText(newValue.getId());
                txtID.setEditable(false);
                txtType.setText(newValue.getType());
                txtKeyMoney.setText(String.valueOf(newValue.getKeyMoney()));
                txtQty.setText(String.valueOf(newValue.getQty()));

                txtID.setDisable(false);
                txtType.setDisable(false);
                txtKeyMoney.setDisable(false);
                txtQty.setDisable(false);
            }
        });
    }

    public void setRoomsTable() {
        tblRooms.getItems().clear();
        List<RoomDTO> allRooms = roomBO.getAll();
        if (allRooms != null) {
            for (RoomDTO room : allRooms) {
                tblRooms.getItems().add(new RoomTM(room.getId(), room.getType(), room.getKeyMoney(), room.getQty()));
            }
        }
    }

    public void saveRoomOnAction() {
        if (roomBO.isExists(txtID.getText())) {
            new Alert(Alert.AlertType.WARNING, "Already Saved Room").show();
            return;
        }
        boolean saved = roomBO.save(new RoomDTO(
                txtID.getText(),
                txtType.getText(),
                Double.parseDouble(txtKeyMoney.getText()),
                Integer.parseInt(txtQty.getText())
        ));
        if (saved) {
            setRoomsTable();
            clear();
            new Alert(Alert.AlertType.CONFIRMATION, "Done Saving").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Error Saving").show();
        }
    }

    public void updateOnAction() {
        boolean saved = roomBO.update(new RoomDTO(
                txtID.getText(),
                txtType.getText(),
                Double.parseDouble(txtKeyMoney.getText()),
                Integer.parseInt(txtQty.getText())
        ));
        if (saved) {
            setRoomsTable();
            clear();
            new Alert(Alert.AlertType.CONFIRMATION, "Done Saving").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Error Saving").show();
        }
    }

    public void deleteOnAction() {
        boolean saved = roomBO.delete(new RoomDTO(
                txtID.getText(),
                txtType.getText(),
                Double.parseDouble(txtKeyMoney.getText()),
                Integer.parseInt(txtQty.getText())
        ));
        if (saved) {
            setRoomsTable();
            clear();
            new Alert(Alert.AlertType.CONFIRMATION, "Done Deleting").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Error Saving").show();
        }
    }

    public void clear() {
        txtQty.clear();
        txtID.clear();
        txtType.clear();
        txtKeyMoney.clear();
    }
}
