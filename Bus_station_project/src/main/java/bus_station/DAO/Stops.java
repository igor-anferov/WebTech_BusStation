package bus_station.DAO;

import bus_station.Main;
import bus_station.model.Part;
import bus_station.model.Stop;

import javax.persistence.EntityManager;

/**
 * Created by Igor on 04.04.2017.
 */
public class Stops {
    private EntityManager entityManager;

    public Stops(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Stop getById (Integer id) {
        String queryText =
                "SELECT c " +
                        "FROM Stop c " +
                        "WHERE c.id = " + id;

        return (Stop) entityManager.createQuery(queryText).getResultList().get(0);
    }

    public void add(Stop s) {
        s.getRun().addStop(s);
        s.getStation().addStop(s);
        entityManager.persist(s);
    }

    public void remove(Stop s) {
        s.getStation().removeStop(s);
        s.getRun().removeStop(s);
        entityManager.remove(s);
    }
}
