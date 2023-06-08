package bio.harshana.dao.custom.impl;

import bio.harshana.projection.UnpaidDetails;
import bio.harshana.util.FactoryConfiguration;
import bio.harshana.dao.custom.QueryDAO;
import org.hibernate.Session;

import java.util.List;

public class QueryDAOImpl implements QueryDAO {
    private Session getSession() {
        return FactoryConfiguration.getInstance().getSession();
    }

    @Override
    public List<UnpaidDetails> getAllUnpaid() {
        try (Session session = getSession()) {
            return session.createQuery("SELECT new bio.harshana.projection.UnpaidDetails(S.stID, S.name,  S.contact, R.id, R.toPaid) FROM Student AS S INNER JOIN Reservation R ON R.student = S.stID WHERE R.status = 'unpaid'").list();
        }
    }
}