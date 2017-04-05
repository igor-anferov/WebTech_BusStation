package bus_station;

import bus_station.DAO.Clients;
import bus_station.DAO.Parts;
import bus_station.DAO.Runs;
import bus_station.DAO.Stations;
import bus_station.model.Client;
import bus_station.model.Order;
import bus_station.model.Part;
import bus_station.model.Station;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.*;

public class Main {
    public static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "bus_station.jpa" );
    public static EntityManager entityManager = entityManagerFactory.createEntityManager();
    public static void main(String[] args) {

//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//        entityManager.getTransaction().begin();
//        Clients.list().forEach(System.out::println);
//        entityManager.getTransaction().commit();
//        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

//        Client Igor = new Client(
//                "Игорь",
//                "Анфёров",
//                "Сергеевич",
//                "Ломоносовский пр-т, 27к11",
//                "+79996662401",
//                "igor-anferov@mail.ru");
//
//        entityManager.getTransaction().begin();
//        Clients.add(Igor);
//        Igor.addOrder(new Order(Igor, entityManager.find(Part.class, 8), 10));
//        entityManager.getTransaction().commit();
//
//        entityManager.close();
//        entityManagerFactory.close();
//
//        entityManagerFactory = Persistence.createEntityManagerFactory( "bus_station.jpa" );
//        entityManager = entityManagerFactory.createEntityManager();
//
//
//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//        entityManager.getTransaction().begin();
//        List<Station> l = Stations.list();
//        List<Station> stops = new ArrayList<Station>();;
//        Station msk = null;
//        for (Station station : l) {
//            if (station.getName().equals("Москва")) {
//                msk = station;
//            }
//            if (station.getName().equals("Тверь")) {
//                stops.add(station);
//            }
//        }
//        List<Part> pl = Parts.list();
//        Runs.getSchedule(pl.get(0).getFrom().getRun()).forEach(System.out::println);
//        entityManager.getTransaction().commit();
//        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//
//        entityManager.getTransaction().begin();
//        Igor.addOrder(new Order(Igor, entityManager.find(Part.class, 8), 1));
//        entityManager.getTransaction().commit();
//
//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//        entityManager.getTransaction().begin();
//        Clients.find(null,null,null,null,null,null,true).forEach(System.out::println);
//        entityManager.getTransaction().commit();
//        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        entityManager.close();
        entityManagerFactory.close();
    }

}
