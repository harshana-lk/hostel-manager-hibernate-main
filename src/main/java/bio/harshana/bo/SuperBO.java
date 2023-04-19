package bio.harshana.bo;

import bio.harshana.dto.SuperDTO;

import javax.persistence.NoResultException;
import java.sql.SQLException;
import java.util.List;

public interface SuperBO<T extends SuperDTO, Integer> {

    boolean save(T obj) throws SQLException, ClassNotFoundException;

    boolean delete(T obj) throws SQLException, ClassNotFoundException;

    boolean update(T obj) throws SQLException, ClassNotFoundException;

    String getLastId() throws NoResultException;

    List<T> getAll() throws SQLException, ClassNotFoundException;

    T get(Integer id) throws SQLException, ClassNotFoundException;

    boolean isExists(Integer id) throws SQLException, ClassNotFoundException;
}
