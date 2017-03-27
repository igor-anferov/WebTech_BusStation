package bus_station;

import bus_station.DAO.Clients;
import bus_station.model.Client;
import bus_station.model.Order;
import bus_station.model.Part;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

public class Main {
    public static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "bus_station.jpa" );
    public static EntityManager entityManager = entityManagerFactory.createEntityManager();
    public static void main(String[] args) {

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        entityManager.getTransaction().begin();
        Clients.list().forEach(System.out::println);
        entityManager.getTransaction().commit();
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        Client Igor = new Client(
                "Игорь",
                "Анфёров",
                "Сергеевич",
                "Ломоносовский пр-т, 27к11",
                "+79996662401",
                "igor-anferov@mail.ru");

        entityManager.getTransaction().begin();
        Clients.add(Igor);
        Igor.addOrder(new Order(Igor, entityManager.find(Part.class, 8), 1));
        entityManager.getTransaction().commit();

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        entityManager.getTransaction().begin();
        Clients.list().forEach(System.out::println);
        entityManager.getTransaction().commit();
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

//        entityManager.getTransaction().begin();
//        Igor.setAddress("Ломоносовский пр-т, 27к11");
//        entityManager.getTransaction().commit();

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        entityManager.getTransaction().begin();
        Clients.getByCompany("Мострансавто").forEach(System.out::println);
        entityManager.getTransaction().commit();
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        entityManager.close();
        entityManagerFactory.close();
    }

}
