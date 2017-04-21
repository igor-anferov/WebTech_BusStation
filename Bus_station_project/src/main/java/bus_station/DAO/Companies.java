package bus_station.DAO;

import bus_station.Main;
import bus_station.model.Company;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Igor on 04.04.2017.
 */
public class Companies {
    private EntityManager entityManager;

    public Companies(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Company> list() {
        String queryText = "SELECT c FROM Company c";
        return entityManager.createQuery(queryText).getResultList();
    }

    public void add(Company c) {
        entityManager.persist(c);
    }

    public void remove(Company c) {
        entityManager.remove(c);
    }
}
