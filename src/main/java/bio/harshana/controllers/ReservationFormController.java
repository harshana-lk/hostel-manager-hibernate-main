package bio.harshana.controllers;

import bio.harshana.bo.BOFactory;
import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import bio.harshana.bo.custom.ReservationBO;
import bio.harshana.bo.custom.RoomBO;
import bio.harshana.bo.custom.StudentBO;
import bio.harshana.dto.ReservationDTO;
import bio.harshana.dto.RoomDTO;
import bio.harshana.dto.StudentDTO;
import bio.harshana.tm.ReservationTM;

import java.util.List;
import java.util.Optional;

public class ReservationFormController {
    private final StudentBO studentBO = (StudentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STUDENT);
    private final RoomBO roomBO = (RoomBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ROOM);

    private final ReservationBO reservationBO = (ReservationBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.RESERVATION);

    public TextField txtReservation;
    public ComboBox<String> cbxStudentID;
    public TextField txtStName;
    public ComboBox<String> cbxRoomID;
    public TextField txtRoomType;
    public TextField txtRoomAvailability;
    public TextField txtKeyMoney;
    public TextField txtPayingAmount;
    public JFXButton btnAdd;
    public TableView<ReservationTM> tblCart;
    public TableColumn colStID;
    public TableColumn colRoomTypeID;
    public TableColumn colRoomType;
    public TableColumn colKeyMoney;
    public TableColumn colPayingAmount;
    public TableColumn colDateFrom;
    public TableColumn colDateTo;
    public TextField txtAddress;
    public TableColumn actions;
    public JFXButton btnPlace;
    public DatePicker pickerFrom;
    public DatePicker pickerTo;

    public void initialize() {
        tblCart.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("studentID"));
        tblCart.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("roomTypeId"));
        tblCart.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("keyMoney"));
        tblCart.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("payingAmount"));
        tblCart.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("fromDate"));
        tblCart.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("toDate"));
        tblCart.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("btn"));


        setStudentCombo();
        setRoomCombo();
        setResID();

        cbxStudentID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                StudentDTO search = studentBO.get(newValue);
                txtStName.setText(search.getName());
                txtAddress.setText(search.getAddress());

            } else {
                txtStName.clear();
                txtAddress.clear();
            }
        });

        cbxRoomID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                RoomDTO search = roomBO.get(newValue);
                txtKeyMoney.setText(String.valueOf(search.getKeyMoney()));
                txtRoomAvailability.setText(String.valueOf(search.getQty()));
                txtRoomType.setText(search.getType());
            } else {
                txtStName.clear();
                txtAddress.clear();
            }
        });

    }

    private void setResID() {
        String id = "";
        id = reservationBO.generateID();

        int newRoomId = Integer.parseInt(id.replace("RID-", "")) + 1;
        txtReservation.setText(String.format("RID-%03d", newRoomId));
    }

    private void setRoomCombo() {
        List<RoomDTO> all = roomBO.getAll();
        for (RoomDTO s : all) {
            cbxRoomID.getItems().add(s.getId());
        }
    }

    private void setStudentCombo() {
        List<StudentDTO> all = studentBO.getAll();
        for (StudentDTO s : all) {
            cbxStudentID.getItems().add(s.getId());
        }
    }

    public void placeOrderOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to confirm this reservation?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get().equals(ButtonType.YES)) {
            ObservableList<ReservationTM> items = tblCart.getItems();
            for (ReservationTM res : items) {
                System.out.println(res);
                ReservationDTO reservationDTO = new ReservationDTO(
                        res.getId(),
                        res.getFromDate(),
                        res.getToDate(),
                        res.getStudentID(),
                        res.getRoomTypeId(),
                        roomBO.get(res.getRoomTypeId()).getKeyMoney() > (res.getPayingAmount())
                                ? "unpaid" : "paid",
                        res.getPayingAmount());
                boolean save = reservationBO.save(reservationDTO);
                if (save) {
                    new Alert(Alert.AlertType.INFORMATION, "Room has been reserved successfully.").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Room has not been reserved successfully.").show();
                }
            }
        }
    }

    public void addToCartOnAction(ActionEvent actionEvent) {
        tblCart.getItems().add(
                new ReservationTM(
                        txtReservation.getText(),
                        pickerFrom.getValue(),
                        pickerTo.getValue(),
                        cbxStudentID.getSelectionModel().getSelectedItem(),
                        cbxRoomID.getSelectionModel().getSelectedItem(),
                        Double.parseDouble(txtKeyMoney.getText()),
                        Double.parseDouble(txtPayingAmount.getText()),
                        new JFXButton("Delete")
                )
        );
        btnAdd.setDisable(true);
    }
}
