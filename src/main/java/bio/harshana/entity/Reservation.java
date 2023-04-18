package bio.harshana.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import bio.harshana.dto.ReservationDTO;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Reservation {
    @Id
    @Column(nullable = false)
    private String id;
    private LocalDate fromDate;
    private LocalDate toDate;
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    @ManyToOne
    @JoinColumn(name = "room_type_id", nullable = false)
    private Room room;
    private String status;
    private double paid;


    public ReservationDTO toReservationDTO() {
        return new ReservationDTO(
                this.id,
                this.fromDate,
                this.toDate,
                this.student.getId(),
                this.room.getId(),
                this.status,
                this.paid
        );
    }
}
