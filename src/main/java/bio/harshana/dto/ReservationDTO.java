package bio.harshana.dto;

import bio.harshana.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import bio.harshana.bo.BOFactory;
import bio.harshana.bo.custom.RoomBO;
import bio.harshana.bo.custom.StudentBO;

import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReservationDTO extends SuperDTO implements Serializable {
    private String id;
    private LocalDate fromDate;
    private LocalDate toDate;
    private StudentDTO student;
    private RoomDTO room;
    private String status;
    private double paid;
    private double toPaid;


    public Reservation toReservation() throws SQLException, ClassNotFoundException {
        StudentBO studentBO = (StudentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STUDENT);
        RoomBO roomBO = (RoomBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ROOM);
        return new Reservation(
                this.id,
                this.fromDate,
                this.toDate,
                studentBO.get(this.student.getId()).toStudent(),
                roomBO.get(this.room.getId()).toRoom(),
                this.status,
                this.paid,
                this.toPaid
        );
    }
}
