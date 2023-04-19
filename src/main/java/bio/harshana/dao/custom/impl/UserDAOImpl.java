package bio.harshana.dao.custom.impl;

import bio.harshana.entity.User;
import bio.harshana.util.FactoryConfiguration;
import bio.harshana.dao.custom.UserDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private Session getSession() {
        return FactoryConfiguration.getInstance().getSession();
    }

    @Override
    public List<User> getAll() {
        try (Session session = getSession()) {
            return session.createQuery("FROM User").list();
        }
    }

    @Override
    public boolean save(User entity) throws SQLException, ClassNotFoundException {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(entity);
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
    public boolean update(User dto) throws SQLException, ClassNotFoundException {
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
    public User search(String s) {
        try (Session session = getSession()) {
            return session.get(User.class, s);
        }
    }

    @Override
    public boolean delete(User s) throws SQLException, ClassNotFoundException {
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
