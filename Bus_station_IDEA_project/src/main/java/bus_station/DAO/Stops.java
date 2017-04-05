package bus_station.DAO;

import bus_station.Main;
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

    public void add(Stop s) {
        entityManager.persist(s);
    }

    public void remove(Stop s) {
        entityManager.remove(s);
    }
}
