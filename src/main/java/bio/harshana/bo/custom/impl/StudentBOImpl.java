package bio.harshana.bo.custom.impl;

import bio.harshana.dao.DAOFactory;
import bio.harshana.dao.DAOTypes;
import bio.harshana.dao.custom.StudentDAO;
import bio.harshana.dto.StudentDTO;
import bio.harshana.bo.custom.StudentBO;

import javax.persistence.NoResultException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentBOImpl implements StudentBO {

    private final StudentDAO studentDAO = (StudentDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.STUDENT);

    @Override
    public boolean save(StudentDTO obj) throws SQLException, ClassNotFoundException {
        return studentDAO.save(obj.toStudent());
    }

    @Override
    public boolean delete(StudentDTO obj) throws SQLException, ClassNotFoundException {
        return studentDAO.delete(obj.toStudent());
    }

    @Override
    public boolean update(StudentDTO obj) throws SQLException, ClassNotFoundException {
        return studentDAO.update(obj.toStudent());
    }

    @Override
    public String getLastId() throws NoResultException {
        return null;
    }

    @Override
    public List<StudentDTO> getAll() {
        List<StudentDTO> result = new ArrayList<>();
        studentDAO.getAll().forEach(e -> {
            result.add(e.toStudentDTO());

        });
        return result;
    }

    @Override
    public StudentDTO get(String id) throws SQLException, ClassNotFoundException {
        return studentDAO.search(id).toStudentDTO();
    }

    @Override
    public boolean isExists(String id) throws SQLException, ClassNotFoundException {
        return studentDAO.search(id) != null;
    }
}
