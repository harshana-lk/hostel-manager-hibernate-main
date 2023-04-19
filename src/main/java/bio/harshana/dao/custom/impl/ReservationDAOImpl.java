package bio.harshana.dao.custom.impl;

import bio.harshana.entity.Reservation;
import bio.harshana.util.FactoryConfiguration;
import bio.harshana.dao.custom.ReservationDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ReservationDAOImpl implements ReservationDAO {

    private Session getSession() {
        return FactoryConfiguration.getInstance().getSession();
    }

    @Override
    public List<Reservation> getAll() {
        try (Session session = getSession()) {
            Query<Reservation> query = session.createQuery("FROM Reservation", Reservation.class);
            return query.getResultList();
        }
    }

    @Override
    public boolean save(Reservation reservation) {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(reservation);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Transaction transaction = getSession().getTransaction();
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        }
    }

    @Override
    public boolean update(Reservation reservation) {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(reservation);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Transaction transaction = getSession().getTransaction();
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        }
    }

    @Override
    public Reservation search(String id) {
        try (Session session = getSession()) {
            return session.get(Reservation.class, id);
        }
    }

    @Override
    public boolean delete(Reservation reservation) {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(reservation);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Transaction transaction = getSession().getTransaction();
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        }
    }

    @Override
    public Query<Reservation> getReservation() {
        try (Session session = getSession()) {
            return session.createQuery("FROM Reservation ORDER BY id DESC", Reservation.class);
        }
    }
}
