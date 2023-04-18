package bio.harshana.bo.custom.impl;

import bio.harshana.dao.DAOFactory;
import bio.harshana.dao.DAOTypes;
import bio.harshana.dao.custom.ReservationDAO;
import bio.harshana.dao.custom.RoomDAO;
import bio.harshana.dto.ReservationDTO;
import bio.harshana.entity.Reservation;
import bio.harshana.entity.Room;
import bio.harshana.util.FactoryConfiguration;
import bio.harshana.bo.custom.ReservationBO;
import org.hibernate.HibernateException;
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

    @Override
    public boolean save(ReservationDTO obj) {


        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Room room = roomBO.search(obj.getRoomTypeId(), session);
            room.setQty(room.getQty() - 1);
            reservationDAO.save(obj.toReservation(), session);
            roomBO.update(room, session);
            transaction.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(ReservationDTO obj) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            reservationDAO.delete(obj.toReservation(), session);
            transaction.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(ReservationDTO obj) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            reservationDAO.update(obj.toReservation(), session);
            transaction.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public String getLastId() throws NoResultException {
        return null;
    }

    @Override
    public List<ReservationDTO> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<ReservationDTO> result = new ArrayList<>();

        try {
            List<Reservation> all = reservationDAO.getAll(session);
            for (Reservation c : all
            ) {
                result.add(c.toReservationDTO());
            }
            return result;

        } catch (NullPointerException e) {
            e.printStackTrace();

            return result;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public ReservationDTO get(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            Reservation room = reservationDAO.search(id, session);
            transaction.commit();
            session.close();
            return room.toReservationDTO();
        } catch (Exception e) {
            session.close();
            return null;
        }
    }

    @Override
    public boolean isExists(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;
        boolean b = false;

        try {
            transaction = session.beginTransaction();
            Reservation search = reservationDAO.search(id, session);
            b = search != null;
            transaction.commit();
        } catch (HibernateException | SQLException | ClassNotFoundException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return b;
    }

    @Override
    public String generateID() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String id = "RID-001";

        try {
            Query query = reservationDAO.getReservation(session);
            query.setMaxResults(1);
            Reservation last = (Reservation) query.uniqueResult();
            id = last != null ? last.getId() : id;
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return id;
    }
}
