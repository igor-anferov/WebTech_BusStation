package bus_station.DAO;

import bus_station.Main;
import bus_station.model.Client;
import bus_station.model.Order;
import bus_station.model.Run;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;


import java.util.List;

public class Clients {

    static public List<Client> find(String firstName, String lastName, String patronymic, String address, String telephone, String email) {
        String queryText = "SELECT c FROM Client c";
        if (firstName != null ||
            lastName  != null ||
            patronymic != null ||
            address != null ||
            telephone != null ||
            email != null )
            queryText += " WHERE";
        Boolean needAnd = false;
        if (firstName  != null) { queryText += (needAnd ? " AND":"")+" c.firstName  = '" + firstName  + "'"; needAnd = true; }
        if (lastName   != null) { queryText += (needAnd ? " AND":"")+" c.lastName   = '" + lastName   + "'"; needAnd = true; }
        if (patronymic != null) { queryText += (needAnd ? " AND":"")+" c.patronymic = '" + patronymic + "'"; needAnd = true; }
        if (address    != null) { queryText += (needAnd ? " AND":"")+" c.address    = '" + address    + "'"; needAnd = true; }
        if (telephone  != null) { queryText += (needAnd ? " AND":"")+" c.telephone  = '" + telephone  + "'"; needAnd = true; }
        if (email      != null) { queryText += (needAnd ? " AND":"")+" c.email      = '" + email      + "'"; needAnd = true; }
        return Main.entityManager.createQuery(queryText).getResultList();
    }

    static public List<Client> list() {
        return find(
                null,
                null,
                null,
                null,
                null,
                null);
    }

    static public void add(Client c) {
        Main.entityManager.persist(c);
    }

    static public void remove(Client c) {
        Main.entityManager.remove(c);
    }

//  Получение списка клиентов, купивших билет на определённый рейс

    static private Query getByRunQuery = Main.entityManager.createQuery(
            "SELECT DISTINCT c " +
                "FROM Client c " +
                "JOIN c.orders o " +
                "JOIN o.part p " +
                "JOIN p.to s " +
                "JOIN s.run r " +
                "WHERE r.number = :run_number ");

    static public List<Client> getByRun (String run_number) {
        return getByRunQuery.setParameter("run_number", run_number).getResultList();
    }

    static private Query getByCompanyQuery = Main.entityManager.createQuery(
            "SELECT DISTINCT c " +
                    "FROM Client c " +
                    "JOIN c.orders o " +
                    "JOIN o.part p " +
                    "JOIN p.to s " +
                    "JOIN s.run r " +
                    "JOIN r.company co " +
                    "WHERE co.name = :company_name ");

    static public List<Client> getByCompany (String company) {
        return getByCompanyQuery.setParameter("company_name", company).getResultList();
    }
}
