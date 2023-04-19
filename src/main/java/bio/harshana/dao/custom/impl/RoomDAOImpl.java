package bio.harshana.dao.custom.impl;

import bio.harshana.entity.Room;
import bio.harshana.util.FactoryConfiguration;
import bio.harshana.dao.custom.RoomDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class RoomDAOImpl implements RoomDAO {

    private Session getSession() {
        return FactoryConfiguration.getInstance().getSession();
    }

    @Override
    public List<Room> getAll() {
        try (Session session = getSession()) {
            return session.createQuery("FROM Room").list();
        }
    }

    @Override
    public boolean save(Room dto) throws SQLException, ClassNotFoundException {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(dto);
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
    public boolean update(Room dto) throws SQLException, ClassNotFoundException {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(dto);
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
    public Room search(String s) {
        try (Session session = getSession()) {
            return session.get(Room.class, s);
        }
    }

    @Override
    public boolean delete(Room s) throws SQLException, ClassNotFoundException {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(s);
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
}
