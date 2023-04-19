package bio.harshana.dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T, ID> extends SuperDAO {
    List<T> getAll();

    boolean save(T dto) throws SQLException, ClassNotFoundException;

    boolean update(T dto) throws SQLException, ClassNotFoundException;

    T search(ID id) throws SQLException, ClassNotFoundException;

    boolean delete(T obj) throws SQLException, ClassNotFoundException;
}
