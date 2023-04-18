package bio.harshana.dto;

import bio.harshana.bo.BOFactory;
import bio.harshana.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import bio.harshana.bo.custom.RoomBO;
import bio.harshana.bo.custom.StudentBO;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReservationDTO extends SuperDTO implements Serializable {
    List<ReservationDTO> reservationDTOS;
    private String id;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String studentID;
    private String roomTypeId;
    private String status;
    private double paid;

    public ReservationDTO(String id, LocalDate fromDate, LocalDate toDate, String studentID, String roomTypeId, String status, double paid) {
        this.id = id;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.studentID = studentID;
        this.roomTypeId = roomTypeId;
        this.status = status;
        this.paid = paid;
    }

    public ReservationDTO(List<ReservationDTO> reservationDTOS) {
        this.reservationDTOS = reservationDTOS;
    }

    public Reservation toReservation() {
        StudentBO studentBO = (StudentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STUDENT);
        RoomBO roomBO = (RoomBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ROOM);
        return new Reservation(
                this.id,
                this.fromDate,
                this.toDate,
                studentBO.get(this.studentID).toStudent(),
                roomBO.get(this.roomTypeId).toRoom(),
                this.status,
                this.paid
        );
    }
}
