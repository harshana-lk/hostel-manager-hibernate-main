package bio.harshana.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import bio.harshana.bo.BOFactory;
import bio.harshana.bo.custom.ReservationBO;
import bio.harshana.bo.custom.RoomBO;
import bio.harshana.bo.custom.StudentBO;
import bio.harshana.dto.ReservationDTO;
import bio.harshana.dto.RoomDTO;
import bio.harshana.dto.StudentDTO;
import bio.harshana.tm.ReservationTM;

import java.sql.SQLException;
import java.time.LocalDate;
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
    public TableColumn<ReservationTM, String> colStID;
    public TableColumn<ReservationTM, String> colRoomTypeID;
    public TableColumn<ReservationTM, String> colKeyMoney;
    public TableColumn<ReservationTM, String> colPayingAmount;
    public TableColumn<ReservationTM, String> colDateFrom;
    public TableColumn<ReservationTM, String> colDateTo;
    public TextField txtAddress;
    public TableColumn<ReservationTM, String> actions;
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
            try {
                if (newValue != null) {
                    StudentDTO search = studentBO.get(newValue);
                    txtStName.setText(search.getName());
                    txtAddress.setText(search.getAddress());
                } else {
                    txtStName.clear();
                    txtAddress.clear();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        cbxRoomID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue != null) {
                    RoomDTO search = roomBO.get(newValue);
                    txtKeyMoney.setText(String.valueOf(search.getKeyMoney()));
                    txtRoomAvailability.setText(String.valueOf(search.getQty()));
                    txtRoomType.setText(search.getType());
                } else {
                    txtStName.clear();
                    txtAddress.clear();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        pickerFrom.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.isBefore(today));
            }
        });

        pickerTo.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate afterOneYear = LocalDate.now().plusYears(1);
                setDisable(empty || date.isBefore(afterOneYear));
            }
        });

        btnAdd.setDisable(true);
    }

    private void setResID() {
        String id;
        id = reservationBO.generateID();

        int newRoomId = Integer.parseInt(id.replace("RID-", "")) + 1;
        txtReservation.setText(String.format("RID-%03d", newRoomId));
    }

    private void setRoomCombo() {
        try {
            roomBO.getAll().forEach(e -> cbxRoomID.getItems().add(e.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setStudentCombo() {
        try {
            studentBO.getAll().forEach(e -> cbxStudentID.getItems().add(e.getId()));
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void placeOrderOnAction() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to confirm this reservation?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();

            if (buttonType.get().equals(ButtonType.YES)) {
                ObservableList<ReservationTM> items = tblCart.getItems();
                for (ReservationTM res : items) {
                    ReservationDTO reservationDTO = new ReservationDTO(
                            res.getId(),
                            res.getFromDate(),
                            res.getToDate(),
                            studentBO.get(res.getStudentID()),
                            roomBO.get(res.getRoomTypeId()),
                            roomBO.get(res.getRoomTypeId()).getKeyMoney() > (res.getPayingAmount())
                                    ? "unpaid" : "paid",
                            res.getPayingAmount(),
                            res.getKeyMoney() - res.getPayingAmount());
                    boolean save = reservationBO.save(reservationDTO);
                    if (save) {
                        cleanAll();
                        new Alert(Alert.AlertType.INFORMATION, "Room has been reserved successfully.").show();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Room has not been reserved successfully.").show();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addToCartOnAction() {
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

    private void allDone() {
        if (!txtKeyMoney.getText().isEmpty()
                && !txtAddress.getText().isEmpty()
                && pickerFrom.getValue() != null) {
            btnAdd.setDisable(!(Double.parseDouble(txtKeyMoney.getText()) >= Double.parseDouble(txtPayingAmount.getText())));
        }
    }


    public void payAmountPressed() {
        allDone();
    }

    private void cleanAll() {
        txtAddress.clear();
        txtKeyMoney.clear();
        txtReservation.clear();
        txtRoomType.clear();
        txtRoomAvailability.clear();
        txtPayingAmount.clear();
        txtStName.clear();
        pickerFrom.setValue(null);
        pickerTo.setValue(null);
        tblCart.setItems(null);
        cbxRoomID.setValue(null);
        cbxStudentID.setValue(null);
    }
}
