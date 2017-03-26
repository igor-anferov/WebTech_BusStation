package bus_station;

import bus_station.model.Client;
import bus_station.model.Order;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "bus_station.jpa" );
        EntityManager entityManager = entityManagerFactory.createEntityManager();

//        entityManager.getTransaction().begin();
//        List<Client> clients = entityManager.createQuery( "from Client", Client.class ).getResultList();
//        for (Client client : clients) {
//            System.out.println(client);
//        }
//        System.out.println("===================================");
//        entityManager.getTransaction().commit();
//
//        entityManager.getTransaction().begin();
//        entityManager.persist( new Client(
//                "Игорь",
//                "Анфёров",
//                "Сергеевич",
//                "Lomonosovsky pr-t, 27k11",
//                "+79996662401",
//                "igor-anferov@mail.ru"
//        ) );
//        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        List<Client> clients1 = entityManager.createQuery( "from Client", Client.class ).getResultList();
        System.out.println(clients1.get(4));
        List<Order> orders = clients1.get(4).getOrders();
        for (Order order : orders) {
            System.out.println(order);
        }
        System.out.println("===================================");
        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();
    }

}
