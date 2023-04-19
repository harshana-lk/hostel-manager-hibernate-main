package bio.harshana.bo.custom.impl;

import bio.harshana.dao.DAOFactory;
import bio.harshana.dao.DAOTypes;
import bio.harshana.dao.custom.UserDAO;
import bio.harshana.dto.UserDTO;
import bio.harshana.bo.custom.UserBO;

import javax.persistence.NoResultException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO {
    private final UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.USER);

    @Override
    public boolean save(UserDTO obj) throws SQLException, ClassNotFoundException {
        return userDAO.save(obj.toUserDTO());
    }

    @Override
    public boolean delete(UserDTO obj) throws SQLException, ClassNotFoundException {
        return userDAO.delete(obj.toUserDTO());
    }

    @Override
    public boolean update(UserDTO obj) throws SQLException, ClassNotFoundException {
        return userDAO.update(obj.toUserDTO());
    }

    @Override
    public String getLastId() throws NoResultException {
        return null;
    }


    @Override
    public List<UserDTO> getAll() {
        List<UserDTO> result = new ArrayList<>();
        userDAO.getAll().forEach(e -> {
            result.add(e.toUserDTO());
        });
        return result;
    }

    @Override
    public UserDTO get(Integer id) {
        return null;
    }

    @Override
    public boolean isExists(Integer id) {
        return false;
    }
}
