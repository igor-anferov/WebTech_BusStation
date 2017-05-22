package bus_station.DAO;

import bus_station.Main;
import bus_station.model.Client;
import bus_station.model.Station;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Igor on 04.04.2017.
 */
public class Stations {
    private EntityManager entityManager;

    public Stations(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Station> list() {
        String queryText = "SELECT s FROM Station s";
        return entityManager.createQuery(queryText).getResultList();
    }

    public Station getById (Integer id) {
        String queryText =
                "SELECT c " +
                        "FROM Station c " +
                        "WHERE c.id = " + id;

        return (Station) entityManager.createQuery(queryText).getResultList().get(0);
    }

    public void add(Station s) {
        entityManager.persist(s);
    }

    public void remove(Station s) {
        entityManager.remove(s);
    }
}
