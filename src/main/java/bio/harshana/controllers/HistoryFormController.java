package bio.harshana.controllers;

import bio.harshana.bo.BOFactory;
import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import bio.harshana.bo.custom.ReservationBO;
import bio.harshana.bo.custom.RoomBO;
import bio.harshana.dto.ReservationDTO;
import bio.harshana.dto.RoomDTO;
import bio.harshana.tm.ReservationTM;

import java.util.List;

public class HistoryFormController {

    private final ReservationBO reservationBO = (ReservationBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.RESERVATION);
    private final RoomBO roomBO = (RoomBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ROOM);
    public TextField txtReservation;
    public TextField txtStID;
    public JFXButton btnUpdate;
    public TextField txtRoomID;
    public TextField txtPaid;
    public TextField txtToPaid;
    public TableView<ReservationTM> tblCart;
    public TableColumn colKeyMoney;
    public TableColumn colStID;
    public TableColumn colRoomTypeID;
    public TableColumn colPayingAmount;
    public TableColumn colDateFrom;
    public TableColumn colDateTo;
    public TextField txtPayNow;
    private ReservationDTO reservation;

    public void initialize() {
        tblCart.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("studentID"));
        tblCart.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("roomTypeId"));
        tblCart.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("keyMoney"));
        tblCart.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("payingAmount"));
        tblCart.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("fromDate"));
        tblCart.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("toDate"));

        loadHistory();

        tblCart.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnUpdate.setDisable(false);

            if (newValue != null) {
                reservation = reservationBO.get(newValue.getId());
                RoomDTO roomDTO = roomBO.get(reservation.getRoomTypeId());

                txtPaid.setText(String.valueOf(reservation.getPaid()));
                txtReservation.setText(reservation.getId());
                txtStID.setText(reservation.getStudentID());
                txtRoomID.setText(reservation.getRoomTypeId());
                txtPaid.setText(String.valueOf(reservation.getPaid()));
                txtToPaid.setText(String.valueOf(roomDTO.getKeyMoney() - reservation.getPaid()));

            }
        });
    }

    private void loadHistory() {
        tblCart.getItems().clear();

        List<ReservationDTO> all = reservationBO.getAll();
        if (all != null) {
            for (ReservationDTO res : all) {
                RoomDTO roomDTO = roomBO.get(res.getRoomTypeId());
                ReservationTM reservationTM = new ReservationTM(
                        res.getId(),
                        res.getFromDate(),
                        res.getToDate(),
                        res.getStudentID(),
                        res.getRoomTypeId(),
                        roomDTO.getKeyMoney(),
                        res.getPaid(),
                        new JFXButton()
                );

                tblCart.getItems().add(reservationTM);
            }
        }

    }

    public void updateOnAction() {
        reservation.setPaid(reservation.getPaid() + Double.parseDouble(txtPayNow.getText()));
        if (reservation.getPaid() >= roomBO.get(reservation.getRoomTypeId()).getKeyMoney())
            reservation.setStatus("paid");

        boolean update = reservationBO.update(reservation);
        if (update) {
            loadHistory();
            new Alert(Alert.AlertType.CONFIRMATION, "Done Update").show();
        }
    }
}
