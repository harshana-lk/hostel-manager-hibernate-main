package bio.harshana.dao.custom;

import bio.harshana.projection.UnpaidDetails;
import bio.harshana.dao.SuperDAO;

import java.util.List;

public interface QueryDAO extends SuperDAO {
    List<UnpaidDetails> getAllUnpaid();

}
