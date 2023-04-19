package bio.harshana.dao.custom.impl;

import bio.harshana.entity.Student;
import bio.harshana.util.FactoryConfiguration;
import bio.harshana.dao.custom.StudentDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {

    private Session getSession() {
        return FactoryConfiguration.getInstance().getSession();
    }

    @Override
    public List<Student> getAll() {
        try (Session session = getSession()) {
            return session.createQuery("FROM Student").list();
        }
    }

    @Override
    public boolean save(Student dto) throws SQLException, ClassNotFoundException {
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
    public boolean update(Student dto) throws SQLException, ClassNotFoundException {
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
    public Student search(String s) {
        try (Session session = getSession()) {
            return session.get(Student.class, s);
        }
    }

    @Override
    public boolean delete(Student s) throws SQLException, ClassNotFoundException {
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
