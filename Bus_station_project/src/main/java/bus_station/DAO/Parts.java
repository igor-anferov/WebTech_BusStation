package bus_station.DAO;

import bus_station.Main;
import bus_station.model.Client;
import bus_station.model.Part;
import bus_station.model.Station;
import bus_station.model.Stop;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Igor on 04.04.2017.
 */
public class Parts {
    private EntityManager entityManager;

    public Parts(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Part> find(Station dep_st, Station arr_st, List<Station> stops, Date departure, BigDecimal upper_price, Integer free_seats) {
        Boolean need_AND = false;

        String queryText =
                "SELECT p" +
                " FROM Part p";

        if (stops != null) {
            queryText +=
                " JOIN Stop sf ON p.from = sf.id" +
                " JOIN Stop st ON p.to = st.id" +
                " JOIN Run r ON sf.run = r.id";
            for (Station stop : stops) {
                queryText +=
                    " JOIN Stop si"+ stop.getId() +" ON " +
                            "si"+ stop.getId() +".run = r.id AND " +
                            "si"+ stop.getId() +".departure > sf.departure AND " +
                            "si"+ stop.getId() +".arrival < st.arrival AND " +
                            "si"+ stop.getId() +".station = "+ stop.getId();
            }
        }

        if (dep_st != null ||
                   arr_st != null ||
                   departure != null ||
                   upper_price != null ||
                   free_seats != null) {
            queryText += " WHERE";
        }

        if (dep_st != null) {
            need_AND = true;
            queryText += " p.from.station = " + dep_st.getId();
        }
        if (arr_st != null) {
            if (need_AND)
                queryText += " AND";
            else
                need_AND = true;
            queryText += " p.to.station = " + arr_st.getId();
        }
        if (departure != null) {
            if (need_AND)
                queryText += " AND";
            else
                need_AND = true;

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            queryText += " DATE(p.from.departure) = '" + df.format(departure) + "'";
        }
        if (upper_price != null) {
            if (need_AND)
                queryText += " AND";
            else
                need_AND = true;
            queryText += " p.price <= " + upper_price;
        }
        if (free_seats != null) {
            if (need_AND)
                queryText += " AND";
            else
                need_AND = true;
            queryText += " p.freeSeats.free >= " + free_seats;
        }

        return entityManager.createQuery(queryText).getResultList();
    }

    public Part getById (Integer id) {
        String queryText =
                "SELECT c " +
                        "FROM Part c " +
                        "WHERE c.id = " + id;

        return (Part) entityManager.createQuery(queryText).getResultList().get(0);
    }

    public Part getByStops (Stop from, Stop to) {
        String queryText =
                "SELECT p " +
                        "FROM Part p " +
                        "WHERE p.from.id = " + from.getId() + " AND " +
                              "p.to.id = " + to.getId();

        List<Part> res = entityManager.createQuery(queryText).getResultList();
        if (res.isEmpty())
            return null;
        return res.get(0);
    }

    public void add(Part p) {
        p.getFrom().addPartFrom(p);
        p.getTo().addPartTo(p);
        entityManager.persist(p);
    }

    public void remove(Part p) {
        p.getFrom().removePartFrom(p);
        p.getTo().removePartTo(p);
        entityManager.remove(p);
    }

    public List<Part> list() {
        String queryText = "SELECT p FROM Part p";
        return entityManager.createQuery(queryText).getResultList();
    }

}
