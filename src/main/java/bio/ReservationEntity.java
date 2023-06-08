package bio;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "reservation", schema = "hostel", catalog = "")
public class ReservationEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private String id;
    @Basic
    @Column(name = "fromDate")
    private Date fromDate;
    @Basic
    @Column(name = "paid")
    private double paid;
    @Basic
    @Column(name = "status")
    private String status;
    @Basic
    @Column(name = "toDate")
    private Date toDate;
    @Basic
    @Column(name = "room_type_id")
    private String roomTypeId;
    @Basic
    @Column(name = "student_id")
    private String studentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(String roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationEntity that = (ReservationEntity) o;
        return Double.compare(that.paid, paid) == 0 && Objects.equals(id, that.id) && Objects.equals(fromDate, that.fromDate) && Objects.equals(status, that.status) && Objects.equals(toDate, that.toDate) && Objects.equals(roomTypeId, that.roomTypeId) && Objects.equals(studentId, that.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fromDate, paid, status, toDate, roomTypeId, studentId);
    }
}
