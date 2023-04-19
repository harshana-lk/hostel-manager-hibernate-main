package bio.harshana.dao.custom;

import bio.harshana.entity.Reservation;
import bio.harshana.dao.CrudDAO;
import org.hibernate.query.Query;


public interface ReservationDAO extends CrudDAO<Reservation, String> {
    Query getReservation();
}
