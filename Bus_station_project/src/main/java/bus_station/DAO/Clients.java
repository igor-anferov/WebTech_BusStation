package bus_station.DAO;

import bus_station.model.Client;

import javax.persistence.EntityManager;


import java.util.List;

public class Clients {

    private EntityManager entityManager;

    public Clients(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Client> find(String firstName, String lastName, String patronymic, String address, String telephone, String email, Boolean not_empty_ord_hist) {
        String queryText = "SELECT c FROM Client c";
        if (firstName != null ||
            lastName  != null ||
            patronymic != null ||
            address != null ||
            telephone != null ||
            email != null ||
            not_empty_ord_hist != null && not_empty_ord_hist == true)
            queryText += " WHERE";
        Boolean needAnd = false;
        if (firstName  != null) { queryText += (needAnd ? " AND":"")+" c.firstName  = '" + firstName  + "'"; needAnd = true; }
        if (lastName   != null) { queryText += (needAnd ? " AND":"")+" c.lastName   = '" + lastName   + "'"; needAnd = true; }
        if (patronymic != null) { queryText += (needAnd ? " AND":"")+" c.patronymic = '" + patronymic + "'"; needAnd = true; }
        if (address    != null) { queryText += (needAnd ? " AND":"")+" c.address    = '" + address    + "'"; needAnd = true; }
        if (telephone  != null) { queryText += (needAnd ? " AND":"")+" c.telephone  = '" + telephone  + "'"; needAnd = true; }
        if (email      != null) { queryText += (needAnd ? " AND":"")+" c.email      = '" + email      + "'"; needAnd = true; }
        if (not_empty_ord_hist != null && not_empty_ord_hist == true) { queryText += (needAnd ? " AND":"")+" c.orders IS NOT EMPTY"; needAnd = true; }
        queryText += " ORDER BY lastName";
        return entityManager.createQuery(queryText).getResultList();
    }

    public List<Client> list() {
        String queryText = "SELECT c FROM Client c ORDER BY lastName";
        return entityManager.createQuery(queryText).getResultList();
    }

    public void add(Client c) {
        entityManager.persist(c);
    }

    public void remove(Client c) {
        entityManager.remove(c);
    }

//  Получение списка клиентов, купивших билет на определённый рейс

    public Client getById (Integer id) {
        String queryText =
                "SELECT c " +
                        "FROM Client c " +
                        "WHERE c.id = " + id;

        return (Client) entityManager.createQuery(queryText).getResultList().get(0);
    }

//  Получение списка клиентов, купивших билет на определённый рейс


    public List<Client> getByRun (String run_number) {
        String queryText =
                "SELECT DISTINCT c " +
                        "FROM Client c " +
                        "JOIN c.orders o " +
                        "JOIN o.part p " +
                        "JOIN p.to s " +
                        "JOIN s.run r " +
                        "WHERE r.number = '" + run_number + "'";

        return entityManager.createQuery(queryText).getResultList();
    }

//  Получение списка клиентов, купивших билет на рейс определённой компании

    public List<Client> getByCompany (String company) {
        String queryText =
                "SELECT DISTINCT c " +
                        "FROM Client c " +
                        "JOIN c.orders o " +
                        "JOIN o.part p " +
                        "JOIN p.to s " +
                        "JOIN s.run r " +
                        "JOIN r.company co " +
                        "WHERE co.name = '" + company + "'";

        return entityManager.createQuery(queryText).getResultList();
    }
}
