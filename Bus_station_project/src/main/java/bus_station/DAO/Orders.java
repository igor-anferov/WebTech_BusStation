package bus_station.DAO;

import bus_station.Main;
import bus_station.model.Client;
import bus_station.model.Order;
import bus_station.model.Station;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Igor on 04.04.2017.
 */
public class Orders {
    private EntityManager entityManager;

    public Orders(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Station> list() {
        String queryText = "SELECT o FROM Order o";
        return entityManager.createQuery(queryText).getResultList();
    }

    public Order getById (Integer id) {
        String queryText =
                "SELECT c " +
                        "FROM Order c " +
                        "WHERE c.id = " + id;

        return (Order) entityManager.createQuery(queryText).getResultList().get(0);
    }

    public void add(Order o) {
        o.getClient().addOrder(o);
        o.getPart().addOrder(o);
        entityManager.persist(o);
    }

    public void remove(Order o) {
        o.getClient().removeOrder(o);
        o.getPart().removeOrder(o);
        entityManager.remove(o);
    }
}
