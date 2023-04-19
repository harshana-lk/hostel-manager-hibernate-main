package bio.harshana.bo.custom.impl;

import bio.harshana.dao.DAOFactory;
import bio.harshana.dao.DAOTypes;
import bio.harshana.dao.custom.RoomDAO;
import bio.harshana.dto.RoomDTO;
import bio.harshana.bo.custom.RoomBO;

import javax.persistence.NoResultException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomBOImpl implements RoomBO {

    private final RoomDAO roomDAO = (RoomDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.ROOM);

    @Override
    public boolean save(RoomDTO obj) throws SQLException, ClassNotFoundException {
        return roomDAO.save(obj.toRoom());
    }

    @Override
    public boolean delete(RoomDTO obj) throws SQLException, ClassNotFoundException {
        return roomDAO.delete(obj.toRoom());
    }

    @Override
    public boolean update(RoomDTO obj) throws SQLException, ClassNotFoundException {
        return roomDAO.update(obj.toRoom());
    }

    @Override
    public String getLastId() throws NoResultException {
        return null;
    }

    @Override
    public List<RoomDTO> getAll() {
        List<RoomDTO> result = new ArrayList<>();
        roomDAO.getAll().forEach(e -> result.add(e.toRoomDTO()));
        return result;
    }

    @Override
    public RoomDTO get(String id) throws SQLException, ClassNotFoundException {
        return roomDAO.search(id).toRoomDTO();
    }

    @Override
    public boolean isExists(String id) throws SQLException, ClassNotFoundException {
        return roomDAO.search(id) != null;
    }
}
