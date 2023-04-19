package bio.harshana.bo.custom.impl;

import bio.harshana.dao.DAOFactory;
import bio.harshana.dao.DAOTypes;
import bio.harshana.dao.custom.QueryDAO;
import bio.harshana.dao.custom.ReservationDAO;
import bio.harshana.dao.custom.RoomDAO;
import bio.harshana.dto.ReservationDTO;
import bio.harshana.entity.Reservation;
import bio.harshana.entity.Room;
import bio.harshana.projection.UnpaidDetails;
import bio.harshana.util.FactoryConfiguration;
import bio.harshana.bo.custom.ReservationBO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationBOImpl implements ReservationBO {

    private final ReservationDAO reservationDAO = (ReservationDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.RESERVATION);
    private final RoomDAO roomBO = (RoomDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.ROOM);

    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.QUERY_DAO);

    private Session getSession() {
        return FactoryConfiguration.getInstance().getSession();
    }

    @Override
    public boolean save(ReservationDTO obj) throws SQLException, ClassNotFoundException {
        boolean b = false;
        Room room = roomBO.search(obj.getRoom().getId());
        room.setQty(room.getQty() - 1);
        boolean saved = reservationDAO.save(obj.toReservation());
        if (saved) {
            b = roomBO.update(room);
        }
        return b;
    }

    @Override
    public boolean delete(ReservationDTO obj) throws SQLException, ClassNotFoundException {
        return reservationDAO.delete(obj.toReservation());
    }

    @Override
    public boolean update(ReservationDTO obj) throws SQLException, ClassNotFoundException {
        return reservationDAO.update(obj.toReservation());
    }

    @Override
    public String getLastId() throws NoResultException {
        return null;
    }

    @Override
    public List<ReservationDTO> getAll() {
        List<ReservationDTO> result = new ArrayList<>();
        reservationDAO.getAll().forEach(e -> {
            result.add(e.toReservationDTO());
        });
        return result;
    }

    @Override
    public ReservationDTO get(String id) throws SQLException, ClassNotFoundException {
        return reservationDAO.search(id).toReservationDTO();

    }

    @Override
    public boolean isExists(String id) throws SQLException, ClassNotFoundException {
        return reservationDAO.search(id) != null;
    }

    @Override
    public String generateID() {
        String id = "RID-001";
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("FROM Reservation ORDER BY id DESC");
            query.setMaxResults(1);
            Reservation last = (Reservation) query.uniqueResult();
            id = last != null ? last.getId() : id;
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public List<UnpaidDetails> getUnpaidDetails() {
        return queryDAO.getAllUnpaid();
    }
}
