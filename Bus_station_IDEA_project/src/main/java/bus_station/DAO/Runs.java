package bus_station.DAO;

import bus_station.Main;
import bus_station.model.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Runs {
    private EntityManager entityManager;

    public Runs(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Stop> getSchedule(Run r) {
        String queryText = " SELECT s" +
                           " FROM Stop s " +
                           " WHERE s.run = " + r.getId() +
                           " ORDER BY arrival";
        return entityManager.createQuery(queryText).getResultList();
    }

    public void add(Run r) {
        entityManager.persist(r);
    }

    public void remove(Run r) {
        entityManager.remove(r);
    }

    public List<Run> list() {
        String queryText = "SELECT r FROM Run r";
        return entityManager.createQuery(queryText).getResultList();
    }
}